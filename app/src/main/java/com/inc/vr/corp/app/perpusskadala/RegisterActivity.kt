package com.inc.vr.corp.app.perpusskadala

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.inc.vr.corp.app.perpusskadala.R


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.login.LoginActivity
import com.inc.vr.corp.app.perpusskadala.model.UserInfo
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

import timber.log.Timber

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        i_pass.filterMinLength(8)
        i_name.filterMinLength(4)
        b_masuk.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        b_daftar.setOnClickListener {
            regUser(i_name.text,i_email.text,i_pass.text,i_kelas.text)
        }
    }
    fun regUser(name: Editable?, email: Editable?, password: Editable?,kelas: Editable?) {
        val apiService = RestApiService()
        val userInfo = UserInfo(
            id = null,
            name = name?.toString(),
            email = email?.toString(),
                kelas = email?.toString(),
            role = "user",
            password = password?.toString()
        )
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        apiService.addUser(userInfo) {
            Timber.d("info "+userInfo.toString())
            if (it?.email != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Timber.d("inpoooxx"+it.toString())
                Timber.d("inpooo"+it?.email)
                toast("Berhasil. Silahkan login untuk melanjutkan")
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            } else {
                Timber.d("inpooo"+it?.email)
                Timber.d("Error registering new user")
                toast("Error! Silahkan Coba lagi")
            }
            alert.dismiss()
        }
    }
    fun EditText.filterMinLength(min: Int){
        // check minimum length on focus change
        onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) { setLengthError(min) }
        }

        // check minimum length on keyboard done click
        setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { setLengthError(min) }
            false
        }

        // check minimum length on enter key press
        setOnKeyListener { p0, p1, p2 ->
            if (p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                setLengthError(min)
                true
            } else {
                false
            }
        }
    }
    // extension function to check edit text minimum length of characters
    fun EditText.setLengthError(min: Int){
        error = try {
            val value = text.toString().trim()
            if (value.length < min){
                "minimum length $min characters."
            }else{
                // hide soft keyboard
                context.hideSoftKeyboard(this)

                Toast.makeText(context,"You submitted : $value",
                    Toast.LENGTH_SHORT).show()
                null
            }
        }catch (e: Exception){
            "minimum length $min characters."
        }
    }
    // extension function to hide soft keyboard programmatically
    fun Context.hideSoftKeyboard(editText: EditText){
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }
}