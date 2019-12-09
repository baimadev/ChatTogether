package com.example.chattogether.data

data class NewFriend(
    var uid: String?,
    var name: String?,
    var avatar: String?,
    var nickname:String?
) {
    var id: Long? = null
    var msg: String? = null
    //状态：未读、已读、已添加、已拒绝等
    var status: Int? = null
    //请求时间
    var time: Long? = null
    var type:Int=0
}