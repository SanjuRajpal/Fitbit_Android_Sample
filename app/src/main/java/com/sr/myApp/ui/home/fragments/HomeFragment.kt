package com.sr.myApp.ui.home.fragments

import com.sr.myApp.R
import com.sr.myApp.databinding.FragmentHomeBinding
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.ui.auth.data.UserBean
import com.sr.myApp.ui.base.BaseFragment
import com.sr.myApp.ui.base.BaseViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home

    override fun viewModel(): BaseViewModel? = null

    override fun initFragment() {
        val binding = getBinding() as FragmentHomeBinding
        val user = (SharedPrefs.getJsonObject(
            SharedPrefs.LOGIN_DATA,
            UserBean::class.java
        ) as UserBean).user
        binding.user = user
    }
}