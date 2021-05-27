package com.inc.vr.corp.app.perpusskadala.api

import com.inc.vr.corp.app.perpusskadala.adapter.BukuAdapter
import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.acl.Group


class RestApiService {
    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
                object : Callback<UserInfo> {
                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                        val addedUser = response.body()
                        onResult(addedUser)
                    }
                }
        )
    }
    fun loginUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(LoginApi::class.java)
        retrofit.loginUser(userData).enqueue(
                object : Callback<UserInfo> {
                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                        val addedUser = response.body()
                        onResult(addedUser)
                    }
                }
        )
    }
    fun orderBuku(userData: OrderInfo, onResult: (OrderInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(OrderApi::class.java)
        retrofit.orderBuku(userData).enqueue(
            object : Callback<OrderInfo> {
                override fun onFailure(call: Call<OrderInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<OrderInfo>, response: Response<OrderInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
    fun loadBuku(userData: BukuInfo, onResult: (List<BukuInfo>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(BukuApi::class.java)
        retrofit.loadBuku(userData).enqueue(
            object : Callback<List<BukuInfo>> {
                override fun onFailure(call: Call<List<BukuInfo>>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<List<BukuInfo>>, response: Response<List<BukuInfo>>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
        //val response = retrofit.loadBuku(userData)
        //val items = response.body()
        //onResult(items);
}