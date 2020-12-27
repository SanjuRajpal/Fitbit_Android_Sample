package com.sr.myApp.network

import io.reactivex.disposables.Disposable

/**
 * APITask call
 *
 * */

class APITask : BaseAPITask() {

    private val apiCall: APICall = Retrofit.getRetrofit().create(APICall::class.java)

    companion object Singleton {
        fun getInstance(): APITask {
            return APITask()
        }

        const val authToken = 1001
        const val userProfile = 1002
        const val userActivity = 1003

        const val UNAUTHORIZED = 401
    }

    fun doGetAuthToken(
        listener: OnResponseListener,
        params: HashMap<String, Any>
    ): Disposable? {
        return getRequest(apiCall.doGetAuthToken(params), listener, authToken)
    }

    fun doGetUserProfile(
        listener: OnResponseListener
    ): Disposable? {
        return getRequest(apiCall.doGetUserProfile(), listener, userProfile)
    }

    fun doGetUserActivities(
        listener: OnResponseListener,
        date: String
    ): Disposable? {
        return getRequest(apiCall.doGetUserActivities(getUserId(), date), listener, userActivity)
    }

}