package com.example.chattogether.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.chattogether.R

@SuppressLint("Registered")
abstract class BaseActivity<T: ViewDataBinding>: AppCompatActivity() {

    lateinit var dataBinding:T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor= ContextCompat.getColor(this,
            R.color.colorPrimary
        )
        initView()
    }

    abstract fun initView()

    fun  initDatabinding(layoutId:Int){
        dataBinding=DataBindingUtil.setContentView(this,layoutId)
    }

}