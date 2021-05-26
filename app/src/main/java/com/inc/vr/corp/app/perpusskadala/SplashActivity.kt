package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreference:SharedPreference=SharedPreference(this)
        if (sharedPreference.getValueString("name")!=null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}