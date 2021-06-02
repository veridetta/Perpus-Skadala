package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.inc.vr.corp.app.perpusskadala.adapter.BukuAdapter
import com.inc.vr.corp.app.perpusskadala.api.BukuApi
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.api.ServiceBuilder
import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_cari.*
import kotlinx.android.synthetic.main.activity_cari.btn_back
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rc_home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CariActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        val ss = getIntent().getStringExtra("title").toString()
        getBuku(ss,"title")
        Timber.d("isi "+ss);
        kategori_text.text="Menampilkan hasil cari '"+ss+"'"
        btn_back.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
    fun getBuku(title: String, author: String) {
        val apiService= RestApiService()
        val bukuInfo = BukuInfo(
            id = null,
            title = title,
            release_year = null,
            updated_at = null,
            cover_url = null,
            cover_id = null,
            author = author,
            category_id = null,
            created_at = null
        )
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@CariActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        val retrofit = ServiceBuilder.buildService(BukuApi::class.java)
        retrofit.loadBuku(bukuInfo).enqueue(
            object : Callback<List<BukuInfo>> {
                override fun onFailure(call: Call<List<BukuInfo>>, t: Throwable) {
                    Timber.d(t.toString())
                    alert.dismiss()
                }

                override fun onResponse(call: Call<List<BukuInfo>>, response: Response<List<BukuInfo>>) {
                    val addedUser = response.body()
                    if(addedUser!==null){
                        Timber.d(addedUser.toString())
                        val heroesAdapter = BukuAdapter(addedUser)
                        rc_home.apply {
                            layoutManager = GridLayoutManager(this@CariActivity,2)
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