package com.example.maculae.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.maculae.R
import com.example.maculae.app.ApiConfig
import com.example.maculae.model.KulinerModel
import kotlinx.android.synthetic.main.activity_tambah_kuliner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahKuliner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_kuliner)

        uploadkuliner.setOnClickListener{
            tambahKuliner()
        }


//        Menghilangkan loginProgressBar saat pertama kali menjalankan activity ini
        tambahProgressBar.visibility = View.GONE
    }


    private fun tambahKuliner() {
        var messageError = "Kolom Tidak Boleh Kosong"
        if (nama.text.isEmpty()) {
            nama.error = messageError
            nama.requestFocus()
            return
        } else if (toko.text.isEmpty()) {
            toko.error = messageError
            toko.requestFocus()
            return
        }else if (harga.text.isEmpty()) {
            harga.error = messageError
            harga.requestFocus()
            return
        }else if (deskripsi.text.isEmpty()) {
            deskripsi.error = messageError
            deskripsi.requestFocus()
            return
        }else if (url_gambar.text.isEmpty()) {
            url_gambar.error = messageError
            url_gambar.requestFocus()
            return
        }else if (url_map.text.isEmpty()) {
            url_map.error = messageError
            url_map.requestFocus()
            return
        }

//      Menampilkan loginProgressBar saat memulai proses register
        tambahProgressBar.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.insertKuliner(
            nama = nama.text.toString(),
            toko = toko.text.toString(),
            harga = harga.text.toString(),
            deskripsi = deskripsi.text.toString(),
            url_gambar = url_gambar.text.toString(),
            url_map     = url_map.text.toString()
        ).enqueue(object : Callback<KulinerModel> {

            override fun onResponse(call: Call<KulinerModel>, response: Response<KulinerModel>) {

//              Menghilangkan loginProgressBar saat proses selesai
                tambahProgressBar.visibility = View.GONE

                val respon = response.body()!!

                if (response.isSuccessful) {
                    val intent = Intent(this@TambahKuliner, DaftarKulinerAdmin::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        this@TambahKuliner,
                        "Kuliner ${respon.nama} dibuat",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@TambahKuliner,
                        "Error : \n" + respon,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<KulinerModel>, t: Throwable) {

//              Menghilangkan loginProgressBar saat proses selesai
                tambahProgressBar.visibility = View.GONE

                Toast.makeText(
                    this@TambahKuliner,
                    "Error : \n" + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}