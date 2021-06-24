package com.example.maculae.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maculae.R
import com.example.maculae.activity.kulinerAdapter.DaftarKulinerAdapter
import com.example.maculae.app.ApiConfig
import com.example.maculae.model.KulinerModel
import kotlinx.android.synthetic.main.activity_daftar_kuliner_admin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarKulinerAdmin : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    /**
     * ini untuk mendapatkan file MainAdapter.kt
     * (untuk menghubungkan file ini dengan file MainApadter.kt)
     */
    private lateinit var daftarKulinerAdapter: DaftarKulinerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_kuliner_admin)

        btnTambahKuliner.setOnClickListener{
            startActivity(Intent(this, TambahKuliner::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        setupRecycleView()
        getDataFromAPI()
    }

    private fun setupRecycleView() {
        daftarKulinerAdapter = DaftarKulinerAdapter(arrayListOf(), object: DaftarKulinerAdapter.OnAdapterListener {
            override fun onClick(result: KulinerModel) {
                startActivity(
                    Intent( applicationContext, DetailKulinerAdmin::class.java )
                        .putExtra("id", result.id)
                        .putExtra("nama", result.nama)
                        .putExtra("toko", result.toko)
                        .putExtra("harga", result.harga)
                        .putExtra("deskripsi", result.deskripsi)
                        .putExtra("url_gambar", result.url_gambar)
                        .putExtra("url_map", result.url_map)
                )
            }

        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = daftarKulinerAdapter
        }
    }

    private fun getDataFromAPI() {
        progressBar.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.selectKuliner(
            id = ""
        )
            .enqueue(object : Callback<List<KulinerModel>> {
                override fun onResponse(
                    call: Call<List<KulinerModel>>,
                    response: Response<List<KulinerModel>>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful){
                        printing( response.body().toString() )
                        showLog( response.body()!! )
                    }
                }

                override fun onFailure(call: Call<List<KulinerModel>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    printing( t.toString() )
                }

            })
    }

    private fun showLog(kuliners: List<KulinerModel>) {
        daftarKulinerAdapter.setData( kuliners )
//        for (pengguna in penggunas){
//            printing("id: ${pengguna.id}")
//            printing("username: ${pengguna.username}")
//            printing("password: ${pengguna.password}")
//            printing("email: ${pengguna.email}")
//            printing("level: ${pengguna.level}")
//        }
    }

    private fun printing(message: String) {
        Log.d(TAG, message)
    }
}