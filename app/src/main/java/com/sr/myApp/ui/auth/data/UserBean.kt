package com.sr.myApp.ui.auth.data

data class UserBean(
    val user: User
)

data class User(
    val fullName: String,
    val gender: String,
    val height: Double,
    val weight: Double
) {
    val mGender
        get() = "Gender: $gender"

    val mHeight
        get() = "Height: $height"

    val mWeight
        get() = "Weight: $weight"
}