package com.sr.myApp.network

/**
 * API's
 * */
class API {
    companion object {

        const val BASE_URL = "https://api.fitbit.com"

        const val GET_TOKEN="/oauth2/token"
        const val USER_PROFILE="/1/user/-/profile.json"
        const val USER_ACTIVITIES="/1/user/{userId}/activities/date/{date}.json"
    }
}