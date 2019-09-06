package com.example.chattogether.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chattogether.R
import com.example.chattogether.databinding.ActivityAddFriendBinding

import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : BaseActivity<ActivityAddFriendBinding>() {

    override fun initView() {
        initDatabinding(R.layout.activity_add_friend)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        setSupportActionBar(toolbar)

    }

}
