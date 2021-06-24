package com.example.maculae.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maculae.R
import com.example.maculae.app.ApiConfig
import com.example.maculae.model.KulinerModel
import kotlinx.android.synthetic.main.edit_kuliner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditKuliner: AppCompatActivity() {

    private var id = ""
    private var nama = ""
    private var toko = ""
    private var harga = ""
    private var deskripsi = ""
    private var url_gambar = ""
    private var url_map = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_kuliner)

        this.id = intent.getStringExtra("id").toString()
        this.nama = intent.getStringExtra("nama").toString()
        this.toko = intent.getStringExtra("toko").toString()
        this.harga = intent.getStringExtra("harga").toString()
        this.deskripsi = intent.getStringExtra("deskripsi").toString()
        this.url_gambar = intent.getStringExtra("url_gambar").toString()
        this.url_map = intent.getStringExtra("url_map").toString()


        editKuliner.setOnClickListener{
            editKuliner()
        }


//      Menghilangkan loginProgressBar saat pertama kali menjalankan activity ini
        editProgressBar.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()

        etnama          .setText(this.nama)
        ettoko          .setText(this.toko)
        etharga         .setText(this.harga)
        etdeskripsi     .setText(this.deskripsi)
        eturl_gambar    .setText(this.url_gambar)
        eturl_map       .setText(this.url_map)
    }

    private fun editKuliner() {
        if (etnama.text.isEmpty()) {
            etnama.error = "Kolom Nama Tidak Boleh Kosong"
            etnama.requestFocus()

            return
        } else if (ettoko.text.isEmpty()) {
            ettoko.error = "Kolom Email Tidak Boleh Kosong"
            ettoko.requestFocus()

            return
        }else if (etharga.text.isEmpty()) {
            etharga.error = "Kolom Email Tidak Boleh Kosong"
            etharga.requestFocus()
            return
        }else if (etdeskripsi.text.isEmpty()) {
            etdeskripsi.error = "Kolom Email Tidak Boleh Kosong"
            etdeskripsi.requestFocus()
            return
        }else if (eturl_gambar.text.isEmpty()) {
            eturl_gambar.error = "Kolom Email Tidak Boleh Kosong"
            eturl_gambar.requestFocus()
            return
        }else if (eturl_map.text.isEmpty()) {
            eturl_map.error = "Kolom Email Tidak Boleh Kosong"
            eturl_map.requestFocus()
            return
        }

//      Menampilkan loginProgressBar saat proses update dimulai
        editProgressBar.visibility = View.VISIBLE
        var idFromEdit = this.id
        ApiConfig.instanceRetrofit.updateKuliner(
            id = this.id,
            nama = etnama.text.toString(),
            toko = ettoko.text.toString(),
            harga = etharga.text.toString(),
            deskripsi = etdeskripsi.text.toString(),
            url_gambar = eturl_gambar.text.toString(),
            url_map     = eturl_map.text.toString()
        ).enqueue(object : Callback<KulinerModel> {

            override fun onResponse(call: Call<KulinerModel>, response: Response<KulinerModel>) {

//              Menghilangkan editProgressBar proses selesai
                editProgressBar.visibility = View.INVISIBLE

                val respon = response.body()!!

                if (response.isSuccessful) {
                    val intent = Intent(this@EditKuliner, DetailKulinerAdmin::class.java)

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(
                        intent.putExtra("id", idFromEdit)
                                .putExtra("nama", etnama.text.toString())
                                .putExtra("toko", ettoko.text.toString())
                                .putExtra("harga", etharga.text.toString())
                                .putExtra("deskripsi", etdeskripsi.text.toString())
                                .putExtra("url_gambar", eturl_gambar.text.toString())
                                .putExtra("url_map", eturl_map.text.toString())
                    )
                    finish()
                    Toast.makeText(
                        this@EditKuliner,
                        "Kuliner diedit",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@EditKuliner,
                        "Error : \n" + respon,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<KulinerModel>, t: Throwable) {

//              Menghilangkan editProgressBar saat proses selesai
                editProgressBar.visibility = View.INVISIBLE

                Toast.makeText(
                    this@EditKuliner,
                    "Error : \n" + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}