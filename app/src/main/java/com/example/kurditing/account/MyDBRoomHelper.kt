package com.example.kurditing.account

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryTransaction::class, UserName::class], version = 2)
abstract class MyDBRoomHelper : RoomDatabase(){
    abstract fun historyTransactionDAO() : HistoryTransactionDAO
    abstract fun usernameDAO() : UserNameDAO
}