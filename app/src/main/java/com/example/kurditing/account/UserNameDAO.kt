package com.example.kurditing.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserNameDAO {
    @Query("SELECT * FROM UserName")
    fun getAllData() : List<UserName>

    @Insert
    fun insertAll(vararg userName : UserName)

    @Query("UPDATE UserName SET name = :name WHERE _id = 123")
    fun updatdeData(name : String)
}