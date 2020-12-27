package com.sr.myApp.ui.auth

import android.app.ActivityOptions
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import com.sr.myApp.R
import com.sr.myApp.databinding.ActivitySplashBinding
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.ui.base.BaseActivity
import com.sr.myApp.ui.base.BaseViewModel
import com.sr.myApp.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun layoutId(): Int = R.layout.activity_splash

    override fun viewModel(): BaseViewModel? = null

    override fun initActivity() {

        object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (SharedPrefs.isLoggedIn())
                    startActivity(MainActivity::class.java)
                else {
                    val transitionActivityOptions = ActivityOptions
                        .makeSceneTransitionAnimation(
                            this@SplashActivity,
                            Pair.create<View, String>(ivLogo, getString(R.string.app_name))
                        )
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            WelcomeActivity::class.java
                        ),
                        transitionActivityOptions.toBundle()
                    )
                }
                Handler(Looper.getMainLooper()).postDelayed({ this@SplashActivity.finish() }, 1000)
            }
        }.start()
    }
}