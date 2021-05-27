package com.inc.vr.corp.app.perpusskadala.api

import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OrderApi {
    @Headers("Content-Type: application/json")
    @POST("create-order")
    fun orderBuku(@Body userData: OrderInfo): Call<OrderInfo>
}