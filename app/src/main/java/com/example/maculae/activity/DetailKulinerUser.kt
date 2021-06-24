package com.example.maculae.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.maculae.R
import kotlinx.android.synthetic.main.detail_kuliner_user.*
//import java.net.URLDecoder
import java.net.URLEncoder

class DetailKulinerUser : AppCompatActivity() {

    private var id = ""
    private var nama = ""
    private var toko = ""
    private var harga = ""
    private var deskripsi = ""
    private var url_gambar = ""
    private var url_map = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_kuliner_user)

        this.id = intent.getStringExtra("id").toString()
        this.nama = intent.getStringExtra("nama").toString()
        this.toko = intent.getStringExtra("toko").toString()
        this.harga = intent.getStringExtra("harga").toString()
        this.deskripsi = intent.getStringExtra("deskripsi").toString()
        this.url_gambar = intent.getStringExtra("url_gambar").toString()
        this.url_map = intent.getStringExtra("url_map").toString()
    }

    override fun onStart() {
        super.onStart()


        tampilDetail()
    }

    private fun tampilDetail() {
        detailkuliner.text = "Detail "+this.nama
        tvnama.text = this.nama
        tvtoko.text = this.toko
        tvharga.text = "Rp. "+this.harga
        tvdeskripsi.text = this.deskripsi
        Glide.with( this )
            .load( this.url_gambar)  //Ini untuk URL gambar yang ingin dimasukkan, ini berformat gabungan url_gambar dengan id
//            .placeholder(R.drawable.ic_launcher_foreground)     //Ini jika gambar gagal diload akan menampilkan ic launscher background
            .error(R.drawable.ic_launcher_background)
//            .centerCrop()                                       //Ini untuk memotong gambar agar berbentuk kotak, karena mungkin gambar tidak berbentuk kotak
            .into( imageView )                      //Ini lokasi dari imageView yang ingin dimasuki url gambar

        fungsionalitasTombol()
    }

    private fun fungsionalitasTombol() {
        maps.setOnClickListener {
            try{
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(this.url_map)
                    )
                )
            }catch (e: Exception){
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://maps.google.com/")
                    )
                )

            }
        }
    }

}