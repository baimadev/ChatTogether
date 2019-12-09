package com.example.chattogether.data

import cn.bmob.v3.BmobObject

 data class Friend(val user:User, val friendUser:User):BmobObject()