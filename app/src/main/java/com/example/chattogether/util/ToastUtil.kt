package com.example.chattogether.util

import android.util.Log
import android.widget.Toast
import com.example.chattogether.MyApplication

fun toast(str:String){

    Toast.makeText(MyApplication.instance.applicationContext,str,Toast.LENGTH_SHORT).show()
}

fun log(str:String){
    Log.d("xia",str)
}