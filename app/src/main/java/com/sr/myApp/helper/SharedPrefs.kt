package com.sr.myApp.helper


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sr.myApp.ui.MyApp


object SharedPrefs {

    const val AUTH_TOKEN = "authToken"
    const val LOGIN_DATA = "loginDetail"
    private const val SHARED_PREF = "sharedPreference"
    private const val LOGIN = "login"
    private var mPrefrence: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {

        mPrefrence =
            MyApp.getInstance().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        editor = mPrefrence.edit()
    }



    fun putJsonObject(key: String, obj: Any) {
        try {
            val gson = Gson()
            val jsonData = gson.toJson(obj)
            editor.putString(key, jsonData)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getJsonObject(key: String, classname: Class<*>): Any {
        val gson = Gson()
        val json = mPrefrence.getString(key, null)
        return gson.fromJson<Any>(json, classname)
    }

    fun isLoggedIn(): Boolean {
        return mPrefrence.getBoolean(LOGIN, false)
    }

    fun setLoggedIn(boolean: Boolean) {
        editor.putBoolean(LOGIN, boolean)
        editor.apply()
    }

    fun clearPreference() {
        editor.clear().apply()
    }
}
