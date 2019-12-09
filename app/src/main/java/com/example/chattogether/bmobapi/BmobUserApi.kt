package com.example.chattogether.bmobapi

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import cn.bmob.newim.BmobIM
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import com.example.chattogether.util.log
import com.example.chattogether.util.toast
import cn.bmob.v3.BmobQuery
import io.reactivex.Single
import cn.bmob.newim.listener.ConnectListener
import android.text.TextUtils
import cn.bmob.newim.bean.BmobIMUserInfo
import com.example.chattogether.data.User
import cn.bmob.newim.core.ConnectionStatus
import cn.bmob.newim.listener.ConnectStatusChangeListener
import cn.bmob.v3.listener.*


object BmobUserApi {

    /**
     * 登陆rx
     */
    @SuppressLint("CheckResult")
    fun loginByRx(user: User, context: Context): Single<User> {
        return Single.create {
            user.login(object : SaveListener<User>() {
                override fun done(p0: User?, p1: BmobException?) {
                    if (p1 == null) {
                        it.onSuccess(p0!!)
                        connect()
                    } else {
                        it.onError(Throwable(p1.errorCode.toString()))
                    }
                }
            })
        }
    }


    /**
     * 注册rx
     */
    @SuppressLint("CheckResult")
    fun registeredByRx(user: User): Single<User> {
        return Single.create<User> {
            user.signUp(object : SaveListener<User>() {
                override fun done(p0: User?, p1: BmobException?) {
                    if (p1 == null) {
                        it.onSuccess(p0!!)
                    } else {
                        toast(p1.message!!)
                        it.onError(Throwable(p1.errorCode.toString()))
                    }
                }
            })
        }
    }

    /**
     * 连接
     */
    fun connect() {
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        val user = getCurrentUser()
        user?.let {
            if (!TextUtils.isEmpty(user.objectId)) {
                BmobIM.connect(user.objectId, object : ConnectListener() {
                    override fun done(uid: String, e: BmobException?) {
                        if (e == null) {
                            //连接成功
                            updateUserInfo(it)
                        } else {
                            //连接失败
                            toast(e.message!!)
                        }
                    }
                })
            }
        }
    }

    /**
     * 断开连接
     */
    fun disConnect() {
        BmobIM.getInstance().disConnect()
    }

    /**
     * 监听连接状态
     */
    fun getCurrentStatus() {
        //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance()
            .setOnConnectStatusChangeListener(object : ConnectStatusChangeListener() {
                override fun onChange(status: ConnectionStatus) {
                    toast(status.msg)
                    log(BmobIM.getInstance().currentStatus.msg)
                }
            })
    }

    /**
     * 退出登陆
     */
    fun logout() {
        if (isLogin()) {
            log("退出登录")
            disConnect()
            BmobUser.logOut()
        }
    }

    /**
     * 判断当前是否有用户登陆
     */
    fun isLogin(): Boolean {
        return BmobUser.isLogin()
    }

    /**
     * 获得当前登陆用户信息
     */
    fun getCurrentUser(): User? {
        if (BmobUser.isLogin()) {
            val user = BmobUser.getCurrentUser(User::class.java)
            return user
        } else {
            toast("尚未登录，请先登录")
            return null
        }
    }

    /**
     * 更新用户表
     */
    fun updataUser(user: User): Single<BmobException> {
        return Single.create<BmobException> {
            user.update(object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    if (p0 != null) {
                        it.onSuccess(p0)
                    }
                }
            })
        }

    }

    /**
     * 更新本地用户表
     */
    fun updateUserInfo(user: User) {
        val userInfo = BmobIMUserInfo()
        userInfo.userId = user.objectId
        userInfo.name = user.username
        userInfo.avatar = " "
        BmobIM.getInstance().updateUserInfo(userInfo)
    }

    /**
     * 获取本地用户表
     */
    fun getUserInfo(uid: String): BmobIMUserInfo {
        return BmobIM.getInstance().getUserInfo(uid)
    }

    /**
     * 同步控制台数据到缓存中
     */
    private fun fetchUserInfo() {
        BmobUser.fetchUserInfo(object : FetchUserInfoListener<BmobUser>() {
            override fun done(user: BmobUser, e: BmobException?) {
                if (e == null) {
                    val myUser = BmobUser.getCurrentUser<User>(User::class.java)
                    log("更新用户本地缓存信息成功：" + myUser.username + "-" + myUser.age)
                } else {
                    Log.e("error", e.message!!)
                }
            }
        })
    }

    /**
     * 获取控制台最新JSON数据,不同步到缓存
     */
    private fun fetchUserJsonInfo() {
        BmobUser.fetchUserJsonInfo(object : FetchUserInfoListener<String>() {
            override fun done(json: String, e: BmobException?) {
                if (e == null) {
                    Log.e("success", json)
                } else {
                    Log.e("error", e.message!!)
                }
            }
        })
    }

    /**
     * 查询用户表
     */
    fun queryUser2Rx(): Single<MutableList<User>> {
        return Single.create {
            val bmobQuery = BmobQuery<User>()
            bmobQuery.findObjects(object : FindListener<User>() {
                override fun done(p0: MutableList<User>?, p1: BmobException?) {
                    if (p1 == null) {
                        it.onSuccess(p0!!)
                    } else {
                        it.onError(p1)
                    }
                }
            })
        }
    }

    /**
     * 根据uid查询用户
     */
    fun queryUserFromUid(uid: String): Single<User> {
        return Single.create {
            val bmobQuery = BmobQuery<User>()
            bmobQuery.getObject(uid, object : QueryListener<User>() {
                override fun done(p0: User?, p1: BmobException?) {
                    if (p0!= null) {
                        it.onSuccess(p0)
                    }
                    if (p1 != null) {
                        it.onError(p1)
                    }
                }
            })
        }
    }


}