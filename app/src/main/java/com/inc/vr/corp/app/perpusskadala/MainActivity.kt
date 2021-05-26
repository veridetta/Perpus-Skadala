package com.inc.vr.corp.app.perpusskadala

import android.os.Bundle
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
        getBuku("")
        val listHeroes = listOf(
                BukuInfo(title = "Thor", category_id = 1, author = "vr", release_year = 2009, id = null,
                        created_at = null, updated_at = null, cover_id = null,
                        cover_url = "https://res.cloudinary.com/karla190922/image/upload/v1621972925/books_cover/jsvpr3wlbjr2dl7shcki.png"),

                )

    }
    fun getBuku(title: String) {
        val apiService= RestApiService()
        val bukuInfo = BukuInfo(
                id = null,
                title = title,
                release_year = null,
                updated_at = null,
                cover_url = null,
                cover_id = null,
                author = null,
                category_id = null,
                created_at = null
        )
        val retrofit = ServiceBuilder.buildService(BukuApi::class.java)
        retrofit.loadBuku(bukuInfo).enqueue(
            object : Callback<List<BukuInfo>> {
                override fun onFailure(call: Call<List<BukuInfo>>, t: Throwable) {
                    Timber.d(t.toString())
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



