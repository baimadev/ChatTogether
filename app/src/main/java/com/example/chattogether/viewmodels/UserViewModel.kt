package com.example.chattogether.viewmodels


import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.chattogether.R
import com.example.chattogether.data.User
import kotlinx.android.synthetic.main.activity_registered.view.*


class UserViewModel(val user: User) : ViewModel() {

    var username: ObservableField<String> = ObservableField(user.username)
    var password: ObservableField<String> = ObservableField()
    var borth: ObservableField<String> = ObservableField(user.borth)
    var sex: ObservableField<String> = ObservableField(user.sex)
    var sexint: ObservableField<Int> = ObservableField()
    var nickname: ObservableField<String> = ObservableField(user.nickname)
    var avatarUrl:String?=user.avatar?.url
    init {
        username.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.username = this@UserViewModel.username.get()
            }
        })

        password.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.setPassword(this@UserViewModel.password.get())
            }
        })
        borth.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.borth = this@UserViewModel.borth.get()!!
            }
        })
        sex.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.sex = this@UserViewModel.sex.get()!!
            }
        })
        sexint.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.sex = when (this@UserViewModel.sexint.get()) {
                    R.id.man -> "男"
                    R.id.woman -> "女"
                    else -> "程序员"
                }
            }
        })
        nickname.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.nickname=nickname.get()!!
            }
        })
    }


}