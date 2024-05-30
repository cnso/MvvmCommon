package org.jash.common.utils

import android.content.Context
import android.content.SharedPreferences

object StoreUtils {
    private lateinit var sp: SharedPreferences
    fun initialize(context:Context) {
        sp = context.getSharedPreferences("shop", Context.MODE_PRIVATE)
    }
    fun save(key:String, value:String) {
        sp.edit().putString(key, value).apply()
    }
    fun save(key:String, value:Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }
    fun save(key:String, value:Int) {
        sp.edit().putInt(key, value).apply()
    }
    fun save(key:String, value:Set<String>) {
        sp.edit().putStringSet(key, value).apply()
    }
    fun getString(key: String, defaultValue:String) : String = sp.getString(key, defaultValue) ?: defaultValue
    fun getBoolean(key: String, defaultValue:Boolean) : Boolean = sp.getBoolean(key, defaultValue) ?: defaultValue
    fun getInt(key: String, defaultValue:Int) : Int = sp.getInt(key, defaultValue) ?: defaultValue
    fun getStringSet(key: String, defaultValue:Set<String>) : Set<String> = sp.getStringSet(key, defaultValue) ?: defaultValue
}