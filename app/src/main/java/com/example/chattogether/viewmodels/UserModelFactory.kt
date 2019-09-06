package com.example.chattogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattogether.data.User

@Suppress("UNCHECKED_CAST")
class UserModelFactory(private val user:User):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(user) as T
    }
}