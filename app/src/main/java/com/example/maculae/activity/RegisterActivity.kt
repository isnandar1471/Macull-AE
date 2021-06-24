package com.example.maculae.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
//import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.example.maculae.MainActivity
import com.example.maculae.R
import com.example.maculae.model.PenggunaModel
import com.example.maculae.app.ApiConfig
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnregister.setOnClickListener() {
            register()
        }

//        menghilangkan registerProgressBar saat pertama kali menjalankan activity ini
        registerProgressBar.visibility = View.GONE
    }

    private fun register() {
        var errorMessage = "Kolom Tidak Boleh Kosong"
        if (username.text.isEmpty()) {
            username.error = errorMessage
            username.requestFocus()
            return
        } else if (email.text.isEmpty()) {
            email.error = errorMessage
            email.requestFocus()
            return
        } else if (password.text.isEmpty()) {
            password.error = errorMessage
            password.requestFocus()
            return
        }

//        menampilkan registerProgressBar saat mulai memproses register
        registerProgressBar.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.register(
            username.text.toString(),
            email.text.toString(),
            password.text.toString()
        ).enqueue(object : Callback<PenggunaModel> {

            override fun onResponse(call: Call<PenggunaModel>, response: Response<PenggunaModel>) {

//              menyembunyikan registerProgressBar saat proses telah selesai
                registerProgressBar.visibility = View.GONE

                val respon = response.body()!!

                if (response.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Akun anda telah dibuat dengan email \n " +
                             "${respon.email} \n " +
                             "Silakan Login menggukana akun tersebut!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error : \n" + respon,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<PenggunaModel>, t: Throwable) {

//              menyembunyikan registerProgressBar saat proses telah selesai
                registerProgressBar.visibility = View.GONE

                Toast.makeText(
                    this@RegisterActivity,
                    "Error:" + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}