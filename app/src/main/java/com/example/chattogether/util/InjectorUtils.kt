package com.example.chattogether.util

import android.content.Context
import com.example.chattogether.data.AppDatabase
import com.example.chattogether.data.User
import com.example.chattogether.data.UserRepository
import com.example.chattogether.viewmodels.UserModelFactory

object InjectorUtils {


    fun provideUserViewModelFactory(user: User): UserModelFactory {
        return UserModelFactory(user)
    }

    fun getUserRepository(context: Context):UserRepository{
        return UserRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).getUserDao()
        )
    }


}