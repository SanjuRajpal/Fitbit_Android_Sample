package com.sr.myApp.ui.home.fragments

import android.app.DatePickerDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sr.myApp.BR
import com.sr.myApp.R
import com.sr.myApp.databinding.FragmentActivitiesBinding
import com.sr.myApp.helper.addOnScrolledToEnd
import com.sr.myApp.helper.gone
import com.sr.myApp.helper.visible
import com.sr.myApp.ui.base.BaseFragment
import com.sr.myApp.ui.base.BindingAdapter
import com.sr.myApp.ui.home.data.Activity
import com.sr.myApp.ui.home.state.HomeState
import com.sr.myApp.ui.home.viewModel.HomeVM
import kotlinx.android.synthetic.main.fragment_activities.*
import java.util.*
import kotlin.collections.ArrayList

class ActivitiesFragment : BaseFragment<FragmentActivitiesBinding>() {

    private val viewModel: HomeVM by lazy {
        (getViewModel() as HomeVM)
    }
    private var mList = ArrayList<Activity>()

    private var selectedDate = "Today"

    override fun layoutId(): Int = R.layout.fragment_activities

    override fun viewModel() = ViewModelProvider(this).get(HomeVM::class.java)

    override fun initFragment() {
        setCallbacks()
        initAdapter()
        llCalender.setOnClickListener { showCalenderPicker() }
    }

    private fun showCalenderPicker() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                selectedDate = "$year-$monthOfYear-$dayOfMonth"
                tvDate.text = selectedDate
                viewModel.doGetUsersActivities(selectedDate)

            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun setCallbacks() {
        viewModel.state().observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: HomeState) {
        when (state) {
            is HomeState.Initial -> {
                viewModel.doGetUsersActivities(selectedDate)
            }
            is HomeState.Loading -> {
                if (state.isVisible) {
                    progress.visible()
                } else {
                    progress.gone()
                }
            }
            is HomeState.Message -> {
                showMessage(state.error)
            }
            is HomeState.Activities -> {

                if (!state.activities.activities.isNullOrEmpty()) {
                    mList.addAll(state.activities.activities)
                    rvActivities.adapter?.notifyDataSetChanged()
                } else {

                    doAddMockData()
                }
            }
        }
    }

    private fun initAdapter() {
        rvActivities.adapter = BindingAdapter(
            layoutId = R.layout.row_activities,
            br = BR.Activities,
            list = mList
        )

        // add scroll listener for pagination
        rvActivities.addOnScrolledToEnd {
//            if (totalRecord > it) {
//                offset++
//                viewModel.doGetUsersActivities(selectedDate)
//            }
        }
    }

    private fun doAddMockData() {
        for (i in 0 until 10) {
            mList.add(
                Activity(
                    "Activity $i", 233, 220
                )
            )
        }
        initAdapter()
    }
}