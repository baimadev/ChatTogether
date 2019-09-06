package com.example.chattogether.ui.login

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.chattogether.data.User
import com.example.chattogether.ui.home.MainActivity
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.databinding.ActivityLoginBinding
import com.example.chattogether.ui.registered.RegisteredActivity
import com.example.chattogether.util.InjectorUtils
import com.example.chattogether.util.jump2Activity
import com.example.chattogether.util.toast
import com.example.chattogether.viewmodels.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.registered = toRegisteredActivity()
        val user = User("", "")
        binding.user =
            InjectorUtils.provideUserViewModelFactory(user).create(UserViewModel::class.java)
        binding.login = login(user)
    }

    /**
     * 登陆
     */
    private fun login(user: User): View.OnClickListener {
        return View.OnClickListener {
            BmobUserApi.loginByRx(user, this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess{
                    //InjectorUtils.getUserRepository(this).insert(it)
                }
                .subscribe({
                    toast("登陆成功！")
                    jump2Activity(this, MainActivity::class.java)
                    finish()
                }, {
                    when (it.message!!.toInt()) {
                        101 -> toast("密码或账号错误！")
                        201 -> toast("缺失数据！")
                        205 -> toast("没有找到此用户名的用户！")
                    }
                })


        }

    }

    /**
     * 注册
     */
    private fun toRegisteredActivity(): View.OnClickListener {
        return View.OnClickListener {
            jump2Activity(this, RegisteredActivity::class.java)
        }

    }


    private fun requestPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.INTERNET
                )
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 0)

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
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


