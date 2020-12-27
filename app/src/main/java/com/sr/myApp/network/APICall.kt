package com.sr.myApp.network

import com.sr.myApp.ui.auth.data.AuthTokenBean
import com.sr.myApp.ui.auth.data.UserBean
import com.sr.myApp.ui.home.data.ActivitiesBean
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface APICall {

    @FormUrlEncoded
    @POST(API.GET_TOKEN)
    fun doGetAuthToken(@FieldMap param: HashMap<String, Any>): Observable<Response<AuthTokenBean>>

    @GET(API.USER_PROFILE)
    fun doGetUserProfile(): Observable<Response<UserBean>>

    @GET(API.USER_ACTIVITIES)
    fun doGetUserActivities(
        @Path("userId") userId: String,
        @Path("date") date: String
    ): Observable<Response<ActivitiesBean>>
}