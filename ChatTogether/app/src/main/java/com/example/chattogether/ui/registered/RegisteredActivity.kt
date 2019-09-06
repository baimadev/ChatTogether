package com.example.chattogether.ui.registered

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.data.User
import com.example.chattogether.databinding.ActivityRegisteredBinding
import com.example.chattogether.ui.home.MainActivity
import com.example.chattogether.util.InjectorUtils
import com.example.chattogether.util.jump2Activity
import com.example.chattogether.util.toast
import com.example.chattogether.viewmodels.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RegisteredActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisteredBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_registered)
        val user = User("", "")
        binding.user =
            InjectorUtils.provideUserViewModelFactory(user).create(UserViewModel::class.java)
        binding.registered = registered(user)
    }

    /**
     *
     * 注册并登陆
     */
    private fun registered(user: User): View.OnClickListener {
        return View.OnClickListener {
            BmobUserApi.registeredByRx(user)
                .map {
                    BmobUserApi.loginByRx(it, this)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    toast("登陆成功!")
                    jump2Activity(this, MainActivity::class.java)
                    finish()
                }, {
                    when (it.message!!.toInt()) {
                        304 -> toast("请输入账号和密码！")
                        202 -> toast("用户名已存在！")
                        201 -> toast("缺失数据！")
                    }
                })
        }
    }
}