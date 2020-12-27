package com.sr.myApp.ui.auth.states

import com.sr.myApp.ui.auth.data.AuthTokenBean
import com.sr.myApp.ui.auth.data.UserBean

sealed class AuthState {

    object Initial : AuthState()
    data class Loading(val isVisible: Boolean) : AuthState()
    data class Message(val error: String) : AuthState()
    data class AuthTokenSuccess(val authToken: AuthTokenBean) : AuthState()
    data class LoginSuccess(val user: UserBean) : AuthState()
}