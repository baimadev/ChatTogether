package com.example.chattogether.data


import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile

data class User(var sex: String="", var borth: String="", var age:Int=0, var avatar:BmobFile?=null, var  nickname:String="" ) : BmobUser()

