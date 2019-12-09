package com.example.chattogether.bmobapi.Message

import cn.bmob.newim.listener.BmobIMMessageHandler
import cn.bmob.newim.event.OfflineMessageEvent
import cn.bmob.newim.event.MessageEvent
import com.example.chattogether.RxBus


class MessageHandler: BmobIMMessageHandler() {
    override fun onMessageReceive(event: MessageEvent?) {
        //在线消息
        event?.let {
            val messageType=it.message.msgType
            when(messageType){
                "add"-> {
                    val msg=AddFriendMessage()
                    msg.extraMsg=it.message.extra
                    RxBus.instance.postEvent(msg)
                }
                "agree"-> {
                    val msg=AgreeAddFriendMessage()
                    msg.extraMsg=it.message.extra
                    RxBus.instance.postEvent(msg)
                }
            }
        }
    }

    /**
     * 处理离线消息
     */
    override fun onOfflineReceive(event: OfflineMessageEvent?) {

        event?.let { it ->
            val event=it.eventMap
            val eventList=event.values
            eventList.forEach {messageEvent->
                when(messageEvent[0].message.msgType){
                    "add"-> {
                        RxBus.instance.postEvent(messageEvent[0])
                    }
                    "agree"-> {
                        RxBus.instance.postEvent(messageEvent[0])
                    }
                }
            }

        }
    }
}