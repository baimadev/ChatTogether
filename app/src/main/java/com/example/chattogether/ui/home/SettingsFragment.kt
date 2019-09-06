package com.example.chattogether.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobUserApi


class SettingsFragment: Fragment() {


    lateinit var mContext: Context
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context!=null){
            mContext=context
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_settings,container,false)
        val button=view.findViewById<Button>(R.id.bt_logout)
        button.setOnClickListener {
            BmobUserApi.logout()
            val activity=mContext as Activity
            activity.finish()
        }
        return view

    }
}