package com.example.kurditing.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context){
    companion object{
        const val USER_PREFF = "USER_PREFF"
    }

    var sharePreferences = context.getSharedPreferences(USER_PREFF, 0)

    fun setValues(key: String, value: String){
        val editor:SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String) : String? {
        return sharePreferences.getString(key, "")
    }
}