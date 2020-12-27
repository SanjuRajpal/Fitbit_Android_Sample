package com.sr.myApp.ui.auth

import com.sr.myApp.R
import com.sr.myApp.databinding.ActivityWelcomeBinding
import com.sr.myApp.ui.base.BaseActivity
import com.sr.myApp.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    override fun layoutId(): Int = R.layout.activity_welcome

    override fun viewModel(): BaseViewModel? = null

    override fun initActivity() {
        btnLogin.setOnClickListener {
            startActivity(AuthActivity::class.java)
        }
        btnRegister.setOnClickListener {
            startActivity(AuthActivity::class.java)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}