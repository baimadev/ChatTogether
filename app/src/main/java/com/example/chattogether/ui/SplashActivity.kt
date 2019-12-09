package com.example.chattogether.ui



import android.os.Bundle
import com.example.chattogether.ui.login.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.chattogether.util.*
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.ui.home.MainActivity


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.chattogether.R.layout.activity_splash)
        setStatusBarFullTransparent(window)
        Thread.sleep(3000)
       if( BmobUserApi.isLogin()){
            jump2Activity(this, MainActivity::class.java)
            finish()
       }else{
            jump2Activity(this,LoginActivity::class.java)
            finish()
        }

    }


}