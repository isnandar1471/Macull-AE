package com.example.maculae.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.maculae.R

class SplahActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah)

        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}