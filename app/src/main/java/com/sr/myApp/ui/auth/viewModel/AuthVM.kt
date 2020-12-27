package com.sr.myApp.ui.auth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sr.myApp.helper.Consts
import com.sr.myApp.network.APITask
import com.sr.myApp.network.OnResponseListener
import com.sr.myApp.ui.auth.data.AuthTokenBean
import com.sr.myApp.ui.auth.data.UserBean
import com.sr.myApp.ui.auth.states.AuthState
import com.sr.myApp.ui.base.BaseViewModel

class AuthVM : BaseViewModel(), OnResponseListener {

    private val state = MutableLiveData<AuthState>(AuthState.Initial)

    fun state(): LiveData<AuthState> = state

    fun doGetAuthToken(code: String) {
        state.value = (AuthState.Loading(true))
        val params = HashMap<String, Any>()
        params["clientId"] = Consts.clientId
        params["grant_type"] = "authorization_code"
        params["redirect_uri"] = Consts.redirectUrl
        params["code"] = code
        mDisposable = APITask.getInstance().doGetAuthToken(this, params)
    }

    fun doGetUserProfile() {
        state.value = (AuthState.Loading(true))
        mDisposable = APITask.getInstance().doGetUserProfile(this)
    }

    override fun <T> onResponseReceived(response: T, requestCode: Int) {
        when (requestCode) {
            APITask.authToken -> {
                state.value = (AuthState.Loading(false))
                state.value = (AuthState.AuthTokenSuccess(response as AuthTokenBean))

            }
            APITask.userProfile -> {
                state.value = (AuthState.Loading(false))
                state.value = (AuthState.LoginSuccess(response as UserBean))
            }

        }
    }

    override fun onResponseError(message: String, requestCode: Int, responseCode: Int) {
        state.value = (AuthState.Loading(false))
        state.value = (AuthState.Message(message))
    }
}