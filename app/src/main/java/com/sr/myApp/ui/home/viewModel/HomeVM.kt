package com.sr.myApp.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sr.myApp.network.APITask
import com.sr.myApp.network.OnResponseListener
import com.sr.myApp.ui.base.BaseViewModel
import com.sr.myApp.ui.home.data.ActivitiesBean
import com.sr.myApp.ui.home.state.HomeState
import java.util.*

class HomeVM : BaseViewModel(), OnResponseListener {

    private val state = MutableLiveData<HomeState>(HomeState.Initial)

    fun state(): LiveData<HomeState> = state

    fun doGetUsersActivities(date: String) {
        state.value = (HomeState.Loading(true))
        mDisposable = APITask.getInstance().doGetUserActivities(
            this, date.toLowerCase(Locale.getDefault())
        )
    }

    override fun <T> onResponseReceived(response: T, requestCode: Int) {
        when (requestCode) {
            APITask.userActivity -> {
                state.value = (HomeState.Loading(false))
                state.value = (HomeState.Activities(response as ActivitiesBean))
            }
        }
    }

    override fun onResponseError(message: String, requestCode: Int, responseCode: Int) {
        if (responseCode == APITask.UNAUTHORIZED) {
            onUnauthorized()
            return
        }

        state.value = (HomeState.Loading(false))
        state.value = (HomeState.Message(message))
    }
}