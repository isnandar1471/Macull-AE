package com.example.senthil.kotlin_navigationdrawer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maculae.R


class DaftarKulinerFragment : Fragment() {
    private lateinit var viewOfLayout: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewOfLayout = inflater.inflate(R.layout.detail_kuliner_user, container, false)
        
        
        return viewOfLayout
        
       
    }
}
