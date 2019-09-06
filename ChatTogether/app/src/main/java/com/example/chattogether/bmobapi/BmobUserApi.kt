package com.example.chattogether.bmobapi

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.chattogether.data.User
import com.example.chattogether.util.log
import com.example.chattogether.util.toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import cn.bmob.v3.listener.FetchUserInfoListener
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import io.reactivex.Single


object BmobUserApi {


    /**
     * 登陆rx
     */
    @SuppressLint("CheckResult")
    fun loginByRx(user: User, context: Context): Single<User> {

        return Single.create<User> {
            user.login(object : SaveListener<User>() {
                override fun done(p0: User?, p1: BmobException?) {
                    if (p1 == null) {
                        it.onSuccess(p0!!)
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
                        it.onError(Throwable(p1.errorCode.toString()))
                    }
                }
            })
        }
    }

    /**
     * 退出登陆
     */
    fun logout() {
        if (isLogin()) {
            log("退出登录")
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
    private fun queryUser(callback: BmobQueryUserCallback) {

        val bmobQuery = BmobQuery<User>()
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(p0: MutableList<User>?, p1: BmobException?) {
                if (p1 == null) {
                    log("查询成功！")
                    callback.onSuccess(p0, p1)
                } else {
                    log("查询失败！+${p1.message}")
                    callback.onFailure(p0, p1)
                }
            }
        })
    }


}