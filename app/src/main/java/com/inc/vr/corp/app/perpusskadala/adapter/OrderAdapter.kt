package com.inc.vr.corp.app.perpusskadala.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.inc.vr.corp.app.perpusskadala.BookingActivity
import com.inc.vr.corp.app.perpusskadala.MainActivity
import com.inc.vr.corp.app.perpusskadala.R
import com.inc.vr.corp.app.perpusskadala.model.BukuInfo
import com.inc.vr.corp.app.perpusskadala.model.OrderInfo
import kotlinx.android.synthetic.main.riwayat_rc.view.*
import kotlinx.android.synthetic.main.small_content.view.*
import kotlinx.android.synthetic.main.small_content.view.card_home

private var context: Context? = null
class OrderAdapter(private val heroes: List<OrderInfo>) : RecyclerView.Adapter<OrderHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): OrderHolder {
        context = viewGroup.getContext();
        return OrderHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.riwayat_rc,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bindOrder(heroes[position])
    }
}
class OrderHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tanggal = view.i_tgl
    private val jam = view.i_jam
    private val status = view.i_status
    private val ket = view.i_keterangan

    fun bindOrder(buku: OrderInfo) {
        tanggal.text=buku.tanggal
        jam.text=buku.jam
        status.text=buku.status
        ket.text="Anda melakukan order buku dengan id buku adalah " + buku.book_id+" pada tanggal "+buku.tanggal+" dan pukul "+buku.jam

    }
}
