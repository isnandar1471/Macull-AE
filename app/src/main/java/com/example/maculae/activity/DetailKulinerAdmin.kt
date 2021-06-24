package com.example.maculae.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
//import android.widget.Button
import android.widget.Toast
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.maculae.R
import com.example.maculae.app.ApiConfig
import com.example.maculae.model.KulinerModel
import kotlinx.android.synthetic.main.adapter_daftar_kuliner.view.*
import kotlinx.android.synthetic.main.detail_kuliner_admin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class DetailKulinerAdmin : AppCompatActivity() {

    private var id = ""
    private var nama = ""
    private var toko = ""
    private var harga = ""
    private var deskripsi = ""
    private var url_gambar = ""
    private var url_map = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_kuliner_admin)

        this.id = intent.getStringExtra("id").toString()
        this.nama = intent.getStringExtra("nama").toString()
        this.toko = intent.getStringExtra("toko").toString()
        this.harga = intent.getStringExtra("harga").toString()
        this.deskripsi = intent.getStringExtra("deskripsi").toString()
        this.url_gambar = intent.getStringExtra("url_gambar").toString()
        this.url_map = intent.getStringExtra("url_map").toString()

        hapusProgressBar.visibility = View.INVISIBLE
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
            .load( this.url_gambar )  //Ini untuk URL gambar yang ingin dimasukkan, ini berformat gabungan url_gambar dengan id
//            .placeholder(R.drawable.ic_launcher_foreground)     //Ini jika gambar gagal diload akan menampilkan ic launscher background
            .error(R.drawable.ic_launcher_background)
            .centerCrop()                                       //Ini untuk memotong gambar agar berbentuk kotak, karena mungkin gambar tidak berbentuk kotak
            .into( imageView )                      //Ini lokasi dari imageView yang ingin dimasuki url gambar

        fungsionalitasTombol()
    }

    private fun fungsionalitasTombol() {
        edit.setOnClickListener {
            startActivity(
                Intent(applicationContext, EditKuliner::class.java)
                    .putExtra("id", this.id)
                    .putExtra("nama", this.nama)
                    .putExtra("toko", this.toko)
                    .putExtra("harga", this.harga)
                    .putExtra("deskripsi", this.deskripsi)
                    .putExtra("url_gambar", this.url_gambar)
                    .putExtra("url_map", this.url_map)
            )
        }
        hapus.setOnClickListener {
            hapusProgressBar.visibility = View.VISIBLE
            deleteKuliner(this.id)
        }
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

    private fun deleteKuliner(id: String) {
        var namaKuliner = this.nama
        ApiConfig.instanceRetrofit.deleteKuliner(
            id = id
        ).enqueue(object : Callback<KulinerModel> {

            override fun onResponse(call: Call<KulinerModel>, response: Response<KulinerModel>) {

                hapusProgressBar.visibility = View.INVISIBLE

                val respon = response.body()!!

                if (response.isSuccessful && respon.status == "success" ){
                    val intent = Intent(this@DetailKulinerAdmin, DaftarKulinerAdmin::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        this@DetailKulinerAdmin,
                        "Kuliner ${namaKuliner} Hapus",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    Toast.makeText(
                        this@DetailKulinerAdmin,
                        "Error : \n"+respon,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<KulinerModel>, t: Throwable) {

                hapusProgressBar.visibility = View.INVISIBLE

                Toast.makeText(
                    this@DetailKulinerAdmin,
                    "Error : \n"+t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

}