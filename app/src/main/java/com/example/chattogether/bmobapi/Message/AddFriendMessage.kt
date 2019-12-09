package com.example.chattogether.bmobapi.Message

import cn.bmob.newim.bean.BmobIMExtraMessage
import cn.bmob.newim.bean.BmobIMMessage

/**
 * 添加好友请求-自定义消息类型
 */
//TODO 好友管理：9.5、自定义添加好友的消息类型
class AddFriendMessage : BmobIMExtraMessage() {

    var extraMsg:String?=null

    override fun getMsgType(): String {
        //自定义一个`add`的消息类型
        return "add"
    }
    override fun isTransient(): Boolean {
        //设置为true,表明为暂态消息，那么这条消息并不会保存到本地db中，SDK只负责发送出去
        return true
    }
}