package com.example.chattogether.bmobapi

import cn.bmob.v3.exception.BmobException
import com.example.chattogether.data.User

interface  BmobQueryUserCallback {
    fun onSuccess(p0: MutableList<User>?, p1: BmobException?)
    fun onFailure(p0: MutableList<User>?, p1: BmobException?)

}