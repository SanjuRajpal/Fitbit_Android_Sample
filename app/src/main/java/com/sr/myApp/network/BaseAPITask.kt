package com.sr.myApp.network

import android.content.Context
import com.sr.myApp.R
import com.sr.myApp.helper.SharedPrefs
import com.sr.myApp.helper.Utils
import com.sr.myApp.ui.MyApp
import com.sr.myApp.ui.auth.data.UserBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * This base call help to check or filter API request
 *
 * */
open class BaseAPITask {

    private val noInternet = 210

    private fun isInternetAvailable(context: Context): Boolean {
        return Utils.isConnected(context)
    }

    private fun noInternetError(context: Context): String {
        return context.resources.getString(R.string.e_no_internet)
    }

    protected fun <T> getRequest(
        request: Observable<Response<T>>,
        mListener: OnResponseListener,
        requestCode: Int
    ): DisposableObserver<*>? {
        return if (isInternetAvailable(MyApp.getInstance())) {
            request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(APICallback(mListener, requestCode, request))
        } else {
            mListener.onResponseError(noInternetError(MyApp.getInstance()), requestCode, noInternet)
            null
        }
    }

    protected fun getUserId():String{
        val user = (SharedPrefs.getJsonObject(
            SharedPrefs.LOGIN_DATA,
            UserBean::class.java
        ) as UserBean).user

        return user.encodedId
    }

}