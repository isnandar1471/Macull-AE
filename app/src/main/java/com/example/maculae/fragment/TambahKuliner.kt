package com.example.maculae.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.maculae.R


class TambahKuliner : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View? = null
        view = inflater.inflate(R.layout.activity_tambah_kuliner, container, false)
        
        
        return view
    }
    
}