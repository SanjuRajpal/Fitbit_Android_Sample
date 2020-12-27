package com.sr.myApp.network

import android.util.Log
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.json.JSONObject
import retrofit2.Response

class APICallback<T>(
    private val mListener: OnResponseListener,
    private val requestCode: Int,
    val request: Observable<Response<T>>
) :
    DisposableObserver<Response<T>>() {
    val message = "message"
    override fun onComplete() {

    }

    override fun onNext(response: Response<T>) {
        /*- OK(200, "OK"),
        - NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information")
        - NO_CONTENT(204, "No Content")
        - ALREADY_REPORTED(208, "Already Reported")
        - BAD_REQUEST(400, "Bad Request")
        - UNAUTHORIZED(401, "Unauthorized")
        - NOT_FOUNToast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();D(404, "Not Found")
        - INTERNAL_SERVER_ERROR(500, "Internal Server Error")*/

        val jobj: JSONObject?
        jobj = try {
            JSONObject(response.errorBody()?.string())
        } catch (e: Exception) {
            JSONObject("{}")
        }
        try {
            when (response.code()) {
                200 -> mListener.onResponseReceived(response.body(), requestCode)
                203 -> mListener.onResponseError(
                    jobj.getString(message) ?: "Non-Authoritative Information",
                    requestCode,
                    response.code()
                )
                204 -> mListener.onResponseError(
                    "No data available!",
                    requestCode,
                    response.code()
                )
                208 -> mListener.onResponseError(
                    "You have used this address in your product. You can't delete this address",
                    requestCode,
                    response.code()
                )
                302 -> mListener.onResponseError(
                    jobj.getString(message) ?: "Check input",
                    requestCode,
                    response.code()
                )
                400 -> mListener.onResponseError(
                    jobj.getString(message) ?: "Bad Request",
                    requestCode,
                    response.code()
                )

                404 -> mListener.onResponseError(
                    jobj.getString(message) ?: "Not Found",
                    requestCode,
                    response.code()
                )
                500 -> mListener.onResponseError(
                    "Internal Server Error",
                    requestCode,
                    response.code()
                )
                401 -> {
                    mListener.onResponseError(
                        "Unauthorized",
                        requestCode,
                        response.code()
                    )
                }

                else -> mListener.onResponseError(
                    "Something went wrong, Please try later",
                    requestCode,
                    0
                )
            }
        } catch (e: Exception) {
            mListener.onResponseError("Something want wrong. Please try later", requestCode, 0)
            e.printStackTrace()
        }

    }

    override fun onError(e: Throwable) {
        Log.e("TAGGG", "onError-> requestCode : $requestCode, error: ${e.message}")
        if (e is java.net.ConnectException) {
            mListener.onResponseError(e.localizedMessage!!, requestCode, 0)
        } else {
            if (!e.message.isNullOrEmpty())
                mListener.onResponseError(e.message!!, requestCode, 0)
            else
                mListener.onResponseError("Something want wrong. Please try later", requestCode, 0)
        }

    }


}
