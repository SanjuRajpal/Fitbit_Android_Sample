package com.sr.myApp.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sr.myApp.BuildConfig
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.ui.auth.data.AuthTokenBean
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Customize retrofit call helps to connect app with web services
 * Thi call can be initialize at application call
 * */

class Retrofit {

    companion object Singleton {
        private lateinit var mRetrofit: Retrofit

        fun init() {
            val httpClient = OkHttpClient.Builder()

            httpClient.readTimeout(10, TimeUnit.MINUTES)
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.writeTimeout(10, TimeUnit.MINUTES)


            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                builder.header("Accept", "application/json")
                val isLogin = SharedPrefs.isLoggedIn()
                if (isLogin) {
                    /**
                     * user's authorized token appends every request
                     * */

                    val auth = SharedPrefs.getJsonObject(
                        SharedPrefs.AUTH_TOKEN,
                        AuthTokenBean::class.java
                    ) as AuthTokenBean
                    val token =
                        "${auth.tokenType} ${auth.accessToken}"
                    builder.header("Authorization", token)
                } else {
                    /**
                     * default authentication token provided by Fitbit
                     * */
                    builder.header(
                        "Authorization",
                        "Basic MjJCWUxGOjUwYmQyZTQyMTg3YzlhN2U1OWRhM2NmNjU3ZjJkMDFh"
                    )
                }

                builder.method(original.method(), original.body())
                chain.proceed(builder.build())
            }

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            else
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            httpClient.addInterceptor(interceptor)

            val client = httpClient.build()
            client.dispatcher().maxRequests = Integer.MAX_VALUE
            val gson = GsonBuilder()
                .setLenient()
                .create()

            mRetrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }


        fun getRetrofit(): Retrofit {
            return mRetrofit
        }
    }
}