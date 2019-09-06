package com.example.chattogether.ui


import android.app.Activity
import android.os.Bundle
import com.example.chattogether.ui.login.LoginActivity
import com.example.chattogether.util.jump2Activity
import android.Manifest.permission.INTERNET
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


import android.app.AlertDialog


class splashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.chattogether.R.layout.activity_splash)
        requestPermission()
        jump2Activity(this, LoginActivity::class.java)
//        if( BmobUserApi.isLogin()){
//            jump2Activity(this,MainActivity::class.java)
//        }else{
//            jump2Activity(this,LoginActivity::class.java)
//        }
        requestPermission()
    }


    private fun requestPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
               INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    INTERNET
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf( INTERNET),
                   0
                )

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf( INTERNET),
                   0
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0-> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showWaringDialog()
                }
                return
            }
        }

    }

    private fun showWaringDialog() {
        AlertDialog.Builder(this)
            .setTitle("警告！")
            .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
            .setPositiveButton("确定") { _, _ ->
                // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                finish()
            }.show()
    }


}