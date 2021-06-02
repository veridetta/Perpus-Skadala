package com.inc.vr.corp.app.perpusskadala

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.inc.vr.corp.app.perpusskadala.api.RestApiService
import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class BookingActivity : AppCompatActivity() {
    private var currentSelectedDate: Long? = null
    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val tahun = getIntent().getStringExtra("tahun").toString()
        val kategori = getIntent().getStringExtra("kategori").toString()
        val judul = getIntent().getStringExtra("judul").toString()
        val author = getIntent().getStringExtra("author").toString()
        val idx = getIntent().getStringExtra("id").toString()
        val cover_url = getIntent().getStringExtra("cover_url").toString()
        val sharedPreference:SharedPreference=SharedPreference(this)
        val id_user = sharedPreference.getValueInt("id")

        i_title.text=judul
        i_author.text="Author : "+author
        i_keterangan.text="Buku ini masuk dalam kategori Buku "+kategori+", yang diterbitkan pada tahun "+tahun
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
        Glide.with(this).load(cover_url).apply(options).into(bg_img)
        ly_datepiker.setOnClickListener {
            showDatePicker()
        }
        ly_timepiker.setOnClickListener {
            showTimePicker()
        }
        btn_back.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        b_booking.setOnClickListener{
            var tgl_oke = false
            var jam_oke = false
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val dateInString = i_tgl.text.toString()
            val date = formatter.format(formatter.parse(dateInString))

            tgl_oke = !i_tgl.text.equals("0000-00-00")
            jam_oke = !i_jam.text.equals("00:00")
            if(tgl_oke && jam_oke){
                orderbuku(
                    idx.toInt(), id_user, date, i_jam.text.toString()
                )

            }else{
                toast("Error! Silahkan isi data yang ada")
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val selectedDateInMillis = currentSelectedDate ?: System.currentTimeMillis()

        MaterialDatePicker.Builder.datePicker().setSelection(selectedDateInMillis).build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
        }.show(supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSelected(dateTimeStampInMillis: Long) {
        currentSelectedDate = dateTimeStampInMillis
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(
                currentSelectedDate!!
            ), ZoneId.systemDefault()
        )
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        i_tgl.text = dateAsFormattedText
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePicker() {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute

        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .build()
            .apply {
                addOnPositiveButtonClickListener { onTimeSelected(this.hour, this.minute) }
            }.show(supportFragmentManager, MaterialTimePicker::class.java.canonicalName)
    }
    private fun onTimeSelected(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        "$hourAsText:$minuteAsText".also { i_jam.text = it }
    }
    fun  isNetworkAvailbale():Boolean{
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }
    fun orderbuku(id_buku: Int?, id: Int?, tanggal: String, jam: String?) {
        val apiService = RestApiService()
        val sum = "Submitted"
        val userInfo = OrderInfo(
            id = null,
            book_id = id_buku,
            user_id = id,
            tanggal = tanggal,
            jam = jam,
            success = null,
            status = sum
        )
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@BookingActivity)
        alertDialog.setTitle("Processing...")
        alertDialog.setMessage("Data sedang diproses")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        apiService.orderBuku(userInfo) {
            Timber.d("info " + userInfo.toString())
            if (it?.success == true) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Timber.d("inpoooxx" + it.toString())
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("tgl", i_tgl.text)
                intent.putExtra("jam", i_jam.text)
                startActivity(intent)
            } else {
                Timber.d("Error registering new user")
                toast("Error! Silahkan Coba lagi")
            }
            alert.dismiss()
        }
    }
}