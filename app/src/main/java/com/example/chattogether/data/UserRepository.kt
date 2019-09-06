package com.example.chattogether.data

class UserRepository private constructor(private val userDao: UserDao){

    fun getUsers()=userDao.getUsers()

    fun getUser(userName:String)=userDao.getUserByUserName(userName)

    fun insert(user:User){
        userDao.insert(user)
    }
    companion object{
        @Volatile private var instance:UserRepository?=null

        fun getInstance(userDao: UserDao)= instance?: synchronized(this){
            instance?:UserRepository(userDao).also { instance=it }
        }

    }

}