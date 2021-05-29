package com.inc.vr.corp.app.perpusskadala.api

import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import com.inc.vr.corp.app.perpusskadala.model.TokenInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

public interface TokenApi {
    @Headers("Content-Type: application/json")
    @POST("token")
    fun saveToken(@Body tokenInfo: TokenInfo): Call<UserInfo>
}