package com.sr.myApp.ui.auth.data

data class UserBean(
    val user: User
)

data class User(
    val encodedId: String,
    val fullName: String,
    val dateOfBirth: String,
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

    val mDateOfBirth
        get() = "DOB: $dateOfBirth"
}