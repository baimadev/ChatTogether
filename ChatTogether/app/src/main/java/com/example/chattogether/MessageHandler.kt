package com.example.chattogether

import cn.bmob.newim.listener.BmobIMMessageHandler
import cn.bmob.newim.event.OfflineMessageEvent
import cn.bmob.newim.event.MessageEvent



class MessageHandler: BmobIMMessageHandler() {
    override fun onMessageReceive(event: MessageEvent?) {
        //在线消息
    }

    override fun onOfflineReceive(event: OfflineMessageEvent?) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
    }
}