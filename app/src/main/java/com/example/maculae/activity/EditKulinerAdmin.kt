package com.example.maculae.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.maculae.R

class EditKulinerAdmin : AppCompatActivity() {
    private lateinit var edit: Button
    private lateinit var hapus: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detil_edit_admin)

        edit = findViewById(R.id.edit)
        hapus = findViewById(R.id.hapus)

//        edit.setOnClickListener{
//            startActivity(Intent(this, EditKuliner::class.java))
//        }
        hapus.setOnClickListener{
            startActivity(Intent(this, DaftarKulinerAdmin::class.java))
        }






    }
}