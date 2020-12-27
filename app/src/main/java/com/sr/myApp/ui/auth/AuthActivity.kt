package com.sr.myApp.ui.auth

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sr.myApp.R
import com.sr.myApp.databinding.ActivityAuthBinding
import com.sr.myApp.helper.*
import com.sr.myApp.ui.auth.states.AuthState
import com.sr.myApp.ui.auth.viewModel.AuthVM
import com.sr.myApp.ui.base.BaseActivity
import com.sr.myApp.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private val viewModel: AuthVM by lazy {
        (getViewModel() as AuthVM)
    }

    override fun layoutId(): Int = R.layout.activity_auth

    override fun viewModel() = ViewModelProvider(this).get(AuthVM::class.java)

    override fun initActivity() {
        initToolbar()
        setCallbacks()
    }

    /**
     * Initialize WebView
     * */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                if (url.startsWith("${Consts.redirectUrl}/?code=")) {
                    val code = url.split("/?code=")[1].dropLast(4)
                    webView.gone()
                    viewModel.doGetAuthToken(code)
                    webView.clearCache(true)
                }
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100)
                    progress.inVisible()
                else
                    progress.visible()

                super.onProgressChanged(view, newProgress)
            }


        }
        webView.loadUrl(Consts.authURL)
    }

    /**
     * Set callback to handle communication between viewModel and view
     * */
    private fun setCallbacks() {
        viewModel.state().observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: AuthState) {
        when (state) {
            is AuthState.Initial -> {
                initWebView()
            }
            is AuthState.Loading -> {
                if (state.isVisible) {
                    binding.progress.visible()
                } else {
                    binding.progress.gone()
                }
            }
            is AuthState.Message -> {
                showMessage(state.error)
            }
            is AuthState.AuthTokenSuccess -> {
                /**
                 * Store authentication token response into pref.
                 */
                SharedPrefs.putJsonObject(SharedPrefs.AUTH_TOKEN, state.authToken)
                viewModel.doGetUserProfile()
            }
            is AuthState.LoginSuccess -> {
                /**
                 * Store user's data into pref. And move to Home screen
                 */
                SharedPrefs.setLoggedIn(true)
                SharedPrefs.putJsonObject(SharedPrefs.LOGIN_DATA, state.user)
                startActivity(MainActivity::class.java)
                finishAffinity()
            }
        }
    }

}

//https://www.fitbit.com/?code=725553a4a627a7e46f6160a4f0d6d56e82998808
//https://api.fitbit.com/1/user/-/activities/afterDate/2017-10-20/sort/desc/offset/0/limit/20/list.json