package com.example.chattogether.util

import android.app.Activity
import android.content.Context
import android.content.Intent

fun jump2Activity(context: Context, clazz: Class<out Activity>){
    val intent= Intent(context,clazz)
    context.startActivity(intent)
}