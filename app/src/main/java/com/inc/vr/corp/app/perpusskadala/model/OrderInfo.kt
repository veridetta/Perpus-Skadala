package com.inc.vr.corp.app.perpusskadala.model

import com.google.gson.annotations.SerializedName

data class OrderInfo(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("id") val id: Int?,
    @SerializedName("user_id") val user_id: Int?,
    @SerializedName("book_id") val book_id: Int?,
    @SerializedName("status") val status: String,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("tanggal_pengembalian") val tanggal_pengembalian: String,
    @SerializedName("jam") val jam: String?,
)