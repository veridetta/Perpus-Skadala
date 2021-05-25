package com.inc.vr.corp.app.perpusskadala.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("password") val password: String?,
)