package com.inc.vr.corp.app.perpusskadala.model

import com.google.gson.annotations.SerializedName

data class TokenInfo (
    @SerializedName("token") val token: String?,
    @SerializedName("id") val id: Int?,
)