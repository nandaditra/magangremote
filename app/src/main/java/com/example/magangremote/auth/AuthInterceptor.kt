package com.example.magangremote.auth

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private var sharedData : SharedPreferences

    init {
        sharedData = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setInput(key: String, value: String) {
        val editor = sharedData.edit()
        editor.putString(key, value).apply()
    }

    fun getInput(key: String): String? {
        return sharedData.getString(key, null)
    }

    fun setLogin(key: String, isChecked : Boolean) {
        val editor = sharedData.edit()
        editor.putBoolean(key, isChecked).apply()
    }

    fun getLogin(isChecked: String): Boolean {
        return sharedData.getBoolean(isChecked, false)
    }

    fun clear() {
        val editor = sharedData.edit()
        editor.clear().apply()
    }

    companion object {
        const val PREFS_NAME = "sharepreferences"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val IS_LOGIN = "isLogin"
        const val TOKEN = "token"
        const val NAME = "token"
    }
}