package com.example.chattogether.bmobapi

import com.example.chattogether.data.Friend
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.newim.bean.BmobIMMessage
import cn.bmob.newim.listener.MessageSendListener
import com.example.chattogether.bmobapi.Message.AddFriendMessage
import cn.bmob.newim.core.BmobIMClient
import cn.bmob.newim.bean.BmobIMConversation
import cn.bmob.newim.BmobIM
import cn.bmob.newim.bean.BmobIMUserInfo
import com.example.chattogether.util.toast
import com.example.chattogether.bmobapi.Message.AgreeAddFriendMessage
import cn.bmob.v3.listener.SaveListener
import com.example.chattogether.data.NewFriend
import com.example.chattogether.data.User
import com.example.chattogether.util.log
import com.google.gson.Gson
import io.reactivex.Single





object BmobFriendApi {

    /**
     * 发送添加好友的请求
     */
    //TODO 好友管理：9.7、发送添加好友请求,对方的UserInfo
    fun sendAddFriendMessage(info:BmobIMUserInfo) {
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        val conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null)
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        val messageManager =
            BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance)
        val msg = AddFriendMessage()
        val currentUser = BmobUserApi.getCurrentUser()
        msg.content = "很高兴认识你，可以加个好友吗?"//给对方的一个留言信息
        //TODO 这里只是举个例子，其实可以不需要传发送者的信息过去
        val map = HashMap<String,Any>()
        currentUser?.let {
            map.put("name", it.username)//发送者姓名
            map.put("uid", it.objectId)//发送者的uid
            map.put("avatar", Gson().toJson(it.avatar))
            map.put("nickname",it.nickname)
        }
        msg.setExtraMap(map)
        msg.bmobIMUserInfo=info
        messageManager.sendMessage(msg, object : MessageSendListener() {
            override fun done(msg: BmobIMMessage, e: BmobException?) {
                if (e == null) {//发送成功
                    toast("好友请求发送成功，等待验证")
                } else {//发送失败
                    toast("发送失败:" + e.message)
                }
            }
        })
    }

    /**
     * 发送同意添加好友的消息
     */
    //TODO 好友管理：9.8、发送同意添加好友
     fun sendAgreeAddFriendMessage(add: NewFriend, listener: SaveListener<BmobIMMessage>) {
        val info = BmobIMUserInfo(add.uid, add.name, add.avatar)
        //TODO 会话：4.1、创建一个暂态会话入口，发送同意好友请求
        val conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null)
        //TODO 消息：5.1、根据会话入口获取消息管理，发送同意好友请求
        val messageManager =
            BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance)
        //AgreeAddFriendMessage的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        val msg = AgreeAddFriendMessage()
        val currentUser = BmobUserApi.getCurrentUser()
        msg.content = "我通过了你的好友验证请求，我们可以开始 聊天了!"//这句话是直接存储到对方的消息表中的
        val map = HashMap<String,Any>()
        currentUser?.run {
            map["name"] = username
            map["nickname"]=nickname
            map["uid"] = objectId//发送者的uid-方便请求添加的发送方找到该条添加好友的请求
            map["avatar"] = Gson().toJson(avatar)
            msg.setExtraMap(map)
        }
        messageManager.sendMessage(msg, object : MessageSendListener() {
            override fun done(msg: BmobIMMessage, e: BmobException?) {
                if (e == null) {//发送成功
                    //TODO 3、修改本地的好友请求记录
                    listener.done(msg, e)
                } else {//发送失败
                    listener.done(msg, e)
                }
            }
        })
    }


    /**
     * 查询好友
     * @param listener
     */
    //TODO 好友管理：9.2、查询好友
    fun queryFriends(): Single<MutableList<Friend>> {
        return Single.create {
            val query = BmobQuery<Friend>()
            val user =BmobUserApi.getCurrentUser()
            query.addWhereEqualTo("user", user)
            query.include("friendUser")
            query.order("-updatedAt")
            query.findObjects( object : FindListener<Friend>() {
                override fun done(p0: MutableList<Friend>?, p1: BmobException?) {
                    if(p0!=null){
                        it.onSuccess(p0)
                    }
                    if(p1!=null){
                        it.onError(p1)
                    }
                }
            })
        }
    }

    /**
     * 删除好友
     * @param f
     * @param listener
     */
    //TODO 好友管理：9.3、删除好友
    fun deleteFriend(f: Friend, listener: UpdateListener) {
        f.delete(f.objectId,listener)
    }


    /**
     * TODO 好友管理：9.10、添加到好友表
     * @param add
     * @param listener
     */
    fun agreeAdd(add:NewFriend) {
        val  userFriend = User()
        userFriend.objectId = add.uid
        val currentUser=BmobUserApi.getCurrentUser()
        if(currentUser!=null){
            val friendTab=Friend(currentUser,userFriend)
            friendTab.save(object: SaveListener<String>() {
                override fun done(p0: String?, p1: BmobException?) {
                    if(p1==null){

                    }else{

                    }
                }
            })
        }else{
            log("当前user为null")
        }

}

    /**
     * TODO 好友管理：9.11、收到同意添加好友后添加好友
     *
     * @param uid
     */
     fun addFriend(uid: String) {
        val user = User()
        user.objectId = uid

    }

}