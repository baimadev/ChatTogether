package com.example.chattogether
import android.app.Application
import cn.bmob.newim.BmobIM
import cn.bmob.v3.Bmob
import com.example.chattogether.bmobapi.Message.MessageHandler


class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Bmob.initialize(this, "8dcbce815068d98f5514bf43e5d18188")
        BmobIM.init(this)
        BmobIM.registerDefaultMessageHandler(MessageHandler())
    }
}