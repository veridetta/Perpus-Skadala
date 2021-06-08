package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.inc.vr.corp.app.perpusskadala.adapter.BukuAdapter
import com.inc.vr.corp.app.perpusskadala.api.BukuApi
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.api.ServiceBuilder
import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.small_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Home"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        val ss = getIntent().getStringExtra("category_id").toString()
        getBuku(ss,"category_id")
        Timber.d("isi "+ss);
        cari_input.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val cari = cari_input.query.toString()
                val intent = Intent(getApplicationContext() , CariActivity::class.java)
                intent.putExtra("title", cari)
                startActivity(intent)
                return false
            }

        })

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
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
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
                            layoutManager = GridLayoutManager(this@MainActivity,2)
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}



