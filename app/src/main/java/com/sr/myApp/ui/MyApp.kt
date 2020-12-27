package com.sr.myApp.ui

import android.app.Application
import com.sr.myApp.network.Retrofit

class MyApp : Application() {

    companion object {
        private lateinit var app: MyApp
        fun getInstance(): MyApp {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Retrofit.init()
    }

}