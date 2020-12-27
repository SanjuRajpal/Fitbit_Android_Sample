package com.sr.myApp.ui.home

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sr.myApp.R
import com.sr.myApp.databinding.ActivityMainBinding
import com.sr.myApp.ui.base.BaseActivity
import com.sr.myApp.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun layoutId(): Int = R.layout.activity_main

    override fun viewModel(): BaseViewModel? = null

    override fun initActivity() {
        /**
         * Enable bottom menu with navigation controller
         */
        val navController = findNavController(R.id.nav_host_fragment)
        bottomMenu.setupWithNavController(navController)
    }

}
