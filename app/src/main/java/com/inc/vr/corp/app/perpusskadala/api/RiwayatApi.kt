package com.inc.vr.corp.app.perpusskadala.api

import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

public interface RiwayatApi {
    @Headers("Content-Type: application/json")
    @POST("order")
    fun riwayatOrder( @Body userData: OrderInfo): Call<List<OrderInfo>>
}