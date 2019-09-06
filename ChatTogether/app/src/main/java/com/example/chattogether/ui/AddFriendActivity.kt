package com.example.chattogether.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chattogether.R

import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : BaseActivity() {
    override fun initView() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        setSupportActionBar(toolbar)

    }

}
