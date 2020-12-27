package com.sr.myApp.ui.home.fragments

import com.sr.myApp.R
import com.sr.myApp.databinding.FragmentMoreBinding
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.ui.auth.WelcomeActivity
import com.sr.myApp.ui.base.BaseFragment
import com.sr.myApp.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : BaseFragment<FragmentMoreBinding>() {
    override fun layoutId(): Int = R.layout.fragment_more

    override fun viewModel(): BaseViewModel? = null

    override fun initFragment() {

        tvLogout.setOnClickListener {
            /**
             * Clear pref and move to Welcome Screen
             */
            SharedPrefs.clearPreference()
            SharedPrefs.setLoggedIn(false)
            startActivity(WelcomeActivity::class.java)
            requireActivity().finishAffinity()
        }
    }
}