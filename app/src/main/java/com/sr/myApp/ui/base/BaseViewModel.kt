package com.sr.myApp.ui.base

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.ui.MyApp
import com.sr.myApp.ui.auth.WelcomeActivity
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected var mDisposable: Disposable? = null


    override fun onCleared() {
        super.onCleared()
        mDisposable?.dispose()
    }

    protected fun onUnauthorized() {
        SharedPrefs.clearPreference()
        val context = MyApp.getInstance()
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        context.startActivity(intent)
    }

}