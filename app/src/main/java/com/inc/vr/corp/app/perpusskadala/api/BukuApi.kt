package com.inc.vr.corp.app.perpusskadala.api

import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

public interface BukuApi {
    @Headers("Content-Type: application/json")
    @POST("book")
    fun loadBuku( @Body userData: BukuInfo): Call<List<BukuInfo>>
}