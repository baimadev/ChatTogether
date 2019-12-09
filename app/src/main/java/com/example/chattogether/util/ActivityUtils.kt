package com.example.chattogether.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

fun jump2Activity(context: Context, clazz: Class<out Activity>) {
    val intent = Intent(context, clazz)
    context.startActivity(intent)
}

/**
 * 全透状态栏
 */
fun setStatusBarFullTransparent(window: Window) {
    if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}