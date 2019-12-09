package com.example.chattogether.bmobapi.Message
import cn.bmob.newim.bean.BmobIMExtraMessage
/**
 * 同意添加好友请求-仅仅只用于发送同意添加好友的消息
 */
//TODO 好友管理：9.6、自定义同意添加好友的消息类型
class AgreeAddFriendMessage :BmobIMExtraMessage(){
    //以下均是从extra里面抽离出来的字段，方便获取
    private val uid: String? = null//最初的发送方
    private val time: Long? = null
    private val msg: String? = null//用于通知栏显示的内容
    var extraMsg:String?=null
    override fun getMsgType(): String {
        return "agree"
    }
    override fun isTransient(): Boolean {
        //此处将同意添加好友的请求设置为false，为了演示怎样向会话表和消息表中新增一个类型，在对方的会话列表中增加`我通过了你的好友验证请求，我们可以开始聊天了!`这样的类型
        return false
    }

}