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
import kotlinx.android.synthetic.main.small_content.view.*

private var context: Context? = null
class BukuAdapter(private val heroes: List<BukuInfo>) : RecyclerView.Adapter<BukuHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): BukuHolder {
        context = viewGroup.getContext();
        return BukuHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.small_content,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: BukuHolder, position: Int) {
        holder.bindBuku(heroes[position])
    }
}
class BukuHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tkategori = view.kategori
    private val ttahun = view.tahun
    private val ttitle = view.nama
    private val tauhtor = view.author
    private val tlogo = view.img_home
    private val card = view.card_home
    private val code = view.book_code

    fun bindBuku(buku: BukuInfo) {
        var kat=""
        if(buku.category_id==1){
            kat="Pelajaran"
        }
        if(buku.category_id==2){
            kat="Novel"
        }
        if(buku.category_id==3){
            kat="Laporan KP"
        }
        tkategori.text = kat
        ttahun.text= buku.release_year.toString()
        ttitle.text=buku.title
        tauhtor.text="Author : "+buku.author
        code.text=buku.book_code
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
        Glide.with(context).load(buku.cover_url).apply(options).into(tlogo)
        card.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra("kategori", kat)
            intent.putExtra("judul", buku.title)
            intent.putExtra("tahun", buku.release_year.toString())
            intent.putExtra("author", buku.author)
            intent.putExtra("cover_url", buku.cover_url)

            intent.putExtra("id", buku.id.toString())
            context?.startActivity(intent)
        }
    }
}
