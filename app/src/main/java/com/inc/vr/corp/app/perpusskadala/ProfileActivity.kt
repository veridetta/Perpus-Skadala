package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.substring
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.inc.vr.corp.app.perpusskadala.adapter.OrderAdapter
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.api.RiwayatApi
import com.inc.vr.corp.app.perpusskadala.api.ServiceBuilder
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity
import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rc_home
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val sharedPreference:SharedPreference=SharedPreference(this)
        val id_user = sharedPreference.getValueInt("id")
        val nama = sharedPreference.getValueString("name")
        val kelas = sharedPreference.getValueString("kelas")
        i_nama.text=nama.toString()
        i_kelas.text=kelas.toString()
        i_pp.text=nama.toString().substring(0,1)
        getRiwayat(id_user)
        b_logout.setOnClickListener{
            sharedPreference.clearSharedPreference()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun getRiwayat(id_user: Int) {
        val apiService= RestApiService()
        val orderInfo = OrderInfo(
            id = null,
            book_id = null,
            user_id = id_user,
            tanggal = null.toString(),
            jam = null,
            success = null,
            status = "null"
        )
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@ProfileActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        val retrofit = ServiceBuilder.buildService(RiwayatApi::class.java)
        retrofit.riwayatOrder(orderInfo).enqueue(
            object : Callback<List<OrderInfo>> {
                override fun onFailure(call: Call<List<OrderInfo>>, t: Throwable) {
                    Timber.d(t.toString())
                    alert.dismiss()
                }

                override fun onResponse(
                    call: Call<List<OrderInfo>>,
                    response: Response<List<OrderInfo>>
                ) {
                    val addedUser = response.body()
                    if (addedUser !== null) {
                        Timber.d(addedUser.toString())
                        val heroesAdapter = OrderAdapter(addedUser)
                        rc_home.apply {
                            layoutManager = GridLayoutManager(this@ProfileActivity, 1)
                            adapter = heroesAdapter
                        }
                    }
                    alert.dismiss()
                }
            }
        )
        /*
        apiService.loadBuku(bukuInfo) {
            Timber.d(" hasil " + it.toString())
            if (it != null) {

            }
        }

         */
    }
}