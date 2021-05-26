package com.inc.vr.corp.app.perpusskadala.model

import com.google.gson.annotations.SerializedName




public class BukuResult {
    @SerializedName("BukuResult")
    var BukuResult: ArrayList<BukuInfo?>? = null

    fun getResult(): ArrayList<BukuInfo?>? {
        return BukuResult
    }

    fun setResult(result: ArrayList<BukuInfo?>?) {
        this.BukuResult = result
    }
}