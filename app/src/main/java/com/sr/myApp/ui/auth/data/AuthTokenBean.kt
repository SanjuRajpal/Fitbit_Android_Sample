package com.sr.myApp.ui.auth.data

import com.google.gson.annotations.SerializedName

data class AuthTokenBean(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user_id")
    val userId: String
)