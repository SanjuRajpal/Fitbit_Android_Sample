package com.sr.myApp.network

/**
 * This interface help to communicate API call's with view models
 * */

interface OnResponseListener {

    fun <T> onResponseReceived(response: T, requestCode: Int)

    fun onResponseError(message: String, requestCode: Int, responseCode: Int)

}
