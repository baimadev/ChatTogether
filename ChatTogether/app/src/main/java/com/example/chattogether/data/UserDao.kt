package com.example.chattogether.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY username")
    fun getUsers():List<User>

    @Query("SELECT * FROM users WHERE username=:name")
    fun getUserByUserName(name:String):User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User)
}