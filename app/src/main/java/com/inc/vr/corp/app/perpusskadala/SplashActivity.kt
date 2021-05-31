package com.inc.vr.corp.app.perpusskadala

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity
import com.inc.vr.corp.app.perpusskadala.model.TokenInfo
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import org.jetbrains.anko.toast
import timber.log.Timber
import java.math.RoundingMode.valueOf

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        tokkken()
    }
    fun tokkken(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.d("getInstanceId failed"+ task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                // Log and toast
                val sharedPreference:SharedPreference=SharedPreference(this)
                Timber.d("Token1 "+sharedPreference.getValueString("token"))
                if (sharedPreference.getValueString("token").equals(token)) {
                    if (sharedPreference.getValueString("name")!=null) {
                        val intent = Intent(this,MenuActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    if (sharedPreference.getValueString("name")!=null) {

                    }else{
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        val koneksi = isNetworkAvailbale()
                        val user_id = sharedPreference.getValueInt("id")
                        if(koneksi){
                            saveToken(user_id, token.toString(),user_id.toString())
                        }
                    }

                }
                // val msg = getString(R.string.msg_token_fmt, token)
                Timber.d("Tokennya "+token)
            })
    }
    fun  isNetworkAvailbale():Boolean{
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }
    fun saveToken(id: Int, token:String, id_url:String) {
        val apiService = RestApiService()
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@SplashActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        val userInfo = TokenInfo(token=token,id=id)
        apiService.saveToken(userInfo) {
            Timber.d("info "+token)
            if (it?.email != null) {
                val sharedPreference:SharedPreference=SharedPreference(this)
                sharedPreference.save("token",token)
                if (sharedPreference.getValueString("name")!=null) {
                    val intent = Intent(this,MenuActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Timber.d("Error registering new user"+token+" ")
                toast("Error! Silahkan Coba lagi")
            }
            alert.dismiss()
        }
    }
}