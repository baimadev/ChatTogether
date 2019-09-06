package com.example.chattogether.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cn.bmob.v3.BmobUser


@Entity(tableName = "users")
data class User(var sex: String, var borth: String) : BmobUser() {
    var age:Int=0
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val userId:String?=null

    var mPassword:String=""
    get() {
        return field
    }
    set(value) {
        field=value
        setPassword(value)
    }
}