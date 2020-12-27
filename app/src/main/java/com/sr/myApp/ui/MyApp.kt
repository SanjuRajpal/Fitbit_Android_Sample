package com.sr.myApp.ui

import android.app.Application
import com.sr.myApp.network.Retrofit

/**
 * Application class
 * Initialize all required things here.
 * */

class MyApp : Application() {

    companion object {
        private lateinit var app: MyApp
        /**
         * Static instance of application call
         * */
        fun getInstance(): MyApp {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        /**
         * Initialize retrofit class
         * */
        Retrofit.init()
    }

}