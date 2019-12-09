package com.example.chattogether.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.chattogether.R

abstract class BaseActivity<T:ViewDataBinding>: AppCompatActivity() {
    lateinit var binding:T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor= ContextCompat.getColor(this,
            R.color.colorPrimary
        )
        initView()
    }
    fun initBinding(layoutId:Int){
        binding=DataBindingUtil.setContentView(this,layoutId)
    }
    abstract fun initView()
}