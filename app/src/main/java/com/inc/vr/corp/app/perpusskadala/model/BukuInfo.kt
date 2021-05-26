package com.inc.vr.corp.app.perpusskadala.model

import com.google.gson.annotations.SerializedName

data class BukuInfo (
    @SerializedName("id") val id : Int?,
    @SerializedName("title") val title : String?,
    @SerializedName("author") val author : String?,
    @SerializedName("release_year") val release_year : Int?,
    @SerializedName("created_at") val created_at : String?,
    @SerializedName("updated_at") val updated_at : String?,
    @SerializedName("cover_url") val cover_url : String?,
    @SerializedName("cover_id") val cover_id : String?,
    @SerializedName("category_id") val category_id : Int?
)