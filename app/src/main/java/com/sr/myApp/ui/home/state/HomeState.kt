package com.sr.myApp.ui.home.state

import com.sr.myApp.ui.home.data.ActivitiesBean

sealed class HomeState {

    object Initial : HomeState()
    data class Loading(val isVisible: Boolean) : HomeState()
    data class Message(val error: String) : HomeState()
    data class Activities(val activities: ActivitiesBean) : HomeState()
}