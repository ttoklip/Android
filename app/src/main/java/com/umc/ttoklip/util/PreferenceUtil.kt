package com.umc.ttoklip.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context:Context) {
    private val prefs:SharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE)

    fun getString(key:String,defValue:String):String{
        return prefs.getString(key,defValue).toString()
    }
    fun setString(key:String,str:String){
        prefs.edit().putString(key,str).apply()
    }
    fun removeString(key:String){
        prefs.edit().remove(key).apply()
    }
    fun getBoolean(key:String,defValue:Boolean):Boolean{
        return prefs.getBoolean(key,defValue)
    }
    fun setBoolean(key:String,boolean:Boolean){
        prefs.edit().putBoolean(key,boolean).apply()
    }
}