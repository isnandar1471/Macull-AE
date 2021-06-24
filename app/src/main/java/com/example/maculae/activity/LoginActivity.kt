package com.example.maculae.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.maculae.MainActivity
import com.example.maculae.R
import com.example.maculae.app.ApiConfig
import com.example.maculae.helper.Constant
import com.example.maculae.helper.PreferenceHelper
import com.example.maculae.model.PenggunaModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var sharedpref: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedpref = PreferenceHelper(this)
        cekSession()

        button.setOnClickListener {
            login()
        }
        tvRegister.setOnClickListener {
            startActivity( Intent( this, RegisterActivity::class.java ) )
        }

//        Menghilangkan loginProgressBar saat pertama kali menjalankan activity ini
        loginProgressBar.visibility = View.INVISIBLE
    }

    private fun cekSession() {
        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN)){
            startActivity( Intent(this, MainActivity::class.java) )
            finish()
        }
    }

    private fun login() {
        if (email.text.isEmpty()) {
            email.error = "Kolom Email Tidak Boleh Kosong"
            email.requestFocus()
            return
        } else if (password.text.isEmpty()) {
            password.error = "Kolom Password Tidak Boleh Kosong"
            password.requestFocus()
            return
        }

//        menampilkan loginProgressbar saat mulai memproses login
        loginProgressBar.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(
            email.text.toString(),
            password.text.toString()).enqueue(object : Callback<PenggunaModel> {
            override fun onResponse(call: Call<PenggunaModel>, response: Response<PenggunaModel>) {

//              menampilkan loginProgressbar saat proses selesai
                loginProgressBar.visibility = View.INVISIBLE

                val respon = response.body()!!

                if (response.isSuccessful && respon.username != "" ){
                    sharedpref.put(Constant.PREF_IS_LOGIN, true)
                    sharedpref.put(Constant.PREF_USERNAME, respon.username)
                    sharedpref.put(Constant.PREF_PASSWORD, respon.password)
                    sharedpref.put(Constant.PREF_EMAIL, respon.email)
                    sharedpref.put(Constant.PREF_LEVEL, respon.level)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        this@LoginActivity,
                        "Usename : " + respon.username+"\n" +
                            "Email   : " + respon.email+"\n" +
                            "Level   : " + respon.level,
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    Toast.makeText(
                        this@LoginActivity,
                        "Error : \n"+respon,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<PenggunaModel>, t: Throwable) {

//              menampilkan loginProgressbar saat proses selesai
                loginProgressBar.visibility = View.INVISIBLE

                Toast.makeText(
                    this@LoginActivity,
                    "Error : \n"+t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}