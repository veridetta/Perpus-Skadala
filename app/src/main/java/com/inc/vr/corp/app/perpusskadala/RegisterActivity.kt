package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import kotlinx.android.synthetic.main.activity_register.*

import timber.log.Timber

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        b_masuk.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        b_daftar.setOnClickListener {
            addDummyUser(i_name.text,i_email.text,i_pass.text)
        }
    }
    fun addDummyUser(name: Editable?, email: Editable?, password: Editable?) {
        val apiService = RestApiService()
        val userInfo = UserInfo(
            id = null,
            name = name?.toString(),
            email = email?.toString(),
            role = "user",
            password = password?.toString()
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