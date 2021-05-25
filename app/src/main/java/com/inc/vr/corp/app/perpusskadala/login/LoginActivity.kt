package com.inc.vr.corp.app.perpusskadala.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inc.vr.corp.app.perpusskadala.BuildConfig
import com.inc.vr.corp.app.perpusskadala.R
import com.inc.vr.corp.app.perpusskadala.RegisterActivity
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        b_masuk.setOnClickListener{
            //addDummyUser()
        }

        b_daftar.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun addDummyUser() {
        val apiService = RestApiService()
         val userInfo = UserInfo(
             id = null,
            name = "Alex",
            email = "alex2n@gmail.com",
            role = "user",
            password = "alex12345"
        )

        apiService.addUser(userInfo) {
            Timber.d("info "+userInfo.toString())
            if (it?.email != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Timber.d("inpoooxx"+it.toString())
                Timber.d("inpooo"+it?.email)
            } else {
                Timber.d("inpooo"+it?.email)
                Timber.d("Error registering new user")
            }
        }
    }
}