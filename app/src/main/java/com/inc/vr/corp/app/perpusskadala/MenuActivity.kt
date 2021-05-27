package com.inc.vr.corp.app.perpusskadala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        c_pelajaran.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category_id", "1")
            startActivity(intent)
        }
        c_novel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category_id", "2")
            startActivity(intent)
        }
        c_laporan_kp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category_id", "3")
            startActivity(intent)
        }
        i_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

}