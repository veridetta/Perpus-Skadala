package com.inc.vr.corp.app.perpusskadala.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.inc.vr.corp.app.perpusskadala.*

import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        b_masuk.setOnClickListener{
            if (isNetworkAvailbale()){
                loginUser(i_email.text,i_pass.text)
            }else{
                toast("Tidak ada koneksi internet")
            }
        }
        val sharedPreference:SharedPreference= SharedPreference(this)
        b_daftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun  isNetworkAvailbale():Boolean{
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }
    fun loginUser(email: Editable?, password: Editable?) {
        val apiService = RestApiService()
         val userInfo = UserInfo(
             id = null,
            name = null,
            email = email?.toString(),
            role = null,
                 kelas = null,
            password = password?.toString()
        )
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        apiService.loginUser(userInfo) {
            Timber.d("info "+userInfo.toString())
            if (it?.email != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Timber.d("inpoooxx"+it.toString())
                Timber.d("inpooo"+it?.email)
                val sharedPreference:SharedPreference= SharedPreference(this)
                it?.name?.let { it1 -> sharedPreference.save("name", it1) }
                sharedPreference.save("email",it?.email)
                it?.kelas?.let { it1 -> sharedPreference.save("kelas", it1) }
                it?.id?.let { it1 -> sharedPreference.save("id", it1) }
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                Timber.d("inpooo"+it?.email)
                Timber.d("Error registering new user")
                toast("Error! Silahkan Coba lagi")
            }
            alert.dismiss()
        }
    }
}