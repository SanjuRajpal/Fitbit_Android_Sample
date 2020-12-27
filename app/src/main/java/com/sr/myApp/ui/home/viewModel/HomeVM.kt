package com.sr.myApp.ui.home.viewModel

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sr.myApp.R
import com.sr.myApp.network.APITask
import com.sr.myApp.network.OnResponseListener
import com.sr.myApp.ui.base.BaseViewModel
import com.sr.myApp.ui.home.data.ActivitiesBean
import com.sr.myApp.ui.home.state.HomeState
import java.util.*

class HomeVM : BaseViewModel(), OnResponseListener {

    private val state = MutableLiveData<HomeState>(HomeState.Initial)

    var selectedDate = "Today"

    fun state(): LiveData<HomeState> = state

    fun onClick(view: View) {
        when (view.id) {
            R.id.llCalender -> {
                showCalenderPicker(view.context)
            }
        }
    }

    private fun showCalenderPicker(context: Context) {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                selectedDate = "$year-$monthOfYear-$dayOfMonth"

            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    fun doGetUsersActivities(date: String) {
        state.value = (HomeState.Loading(true))
        mDisposable = APITask.getInstance().doGetUserActivities(
            this, "92HSNK", date.toLowerCase(Locale.getDefault())
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