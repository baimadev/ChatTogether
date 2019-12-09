package com.example.chattogether.util


import com.example.chattogether.data.User

import com.example.chattogether.viewmodels.UserModelFactory

object InjectorUtils {

    fun provideUserViewModelFactory(user: User): UserModelFactory {
        return UserModelFactory(user)
    }




}