package com.example.kurditing.account

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryTransaction::class), version = 1)
abstract class MyDBRoomHelper : RoomDatabase(){
    abstract fun historyTransactionDAO() : HistoryTransactionDAO
}