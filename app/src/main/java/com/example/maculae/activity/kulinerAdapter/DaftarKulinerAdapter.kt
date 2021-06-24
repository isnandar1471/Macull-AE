package com.example.maculae.activity.kulinerAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maculae.R
import com.example.maculae.model.KulinerModel
import kotlinx.android.synthetic.main.adapter_daftar_kuliner.view.*

class DaftarKulinerAdapter (
    val results:ArrayList<KulinerModel>, val listener: OnAdapterListener
): RecyclerView.Adapter<DaftarKulinerAdapter.ViewHolder>() {

    class ViewHolder (val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_daftar_kuliner, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]

        holder.view.nama.text = result.nama
        holder.view.harga.text = "Rp. "+result.harga
        Glide.with( holder.view )
            .load( result.url_gambar )  //Ini untuk URL gambar yang ingin dimasukkan, ini berformat gabungan url_gambar dengan id
            .placeholder(R.drawable.ic_launcher_foreground)     //Ini jika gambar gagal diload akan menampilkan ic launscher background
            .error(R.drawable.ic_launcher_background)
            .centerCrop()                                       //Ini untuk memotong gambar agar berbentuk kotak, karena mungkin gambar tidak berbentuk kotak
            .into( holder.view.imageView )                      //Ini lokasi dari imageView yang ingin dimasuki url gambar
        holder.view.button.setOnClickListener {
            listener.onClick( result )
        }
    }

    override fun getItemCount() =results.size

    fun setData(data: List<KulinerModel>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(result: KulinerModel)
    }
}