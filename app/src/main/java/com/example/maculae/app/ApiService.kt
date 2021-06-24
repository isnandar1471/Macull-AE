package com.example.maculae.app

import com.example.maculae.model.KulinerModel
import com.example.maculae.model.PenggunaModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("pengguna/insert") // 192.168.100.10/api/register
    fun register(
//        @Field("id") id :String,
        @Field("username") username :String,
        @Field("email") email :String,
        @Field("password") password :String
    ):Call<PenggunaModel>

    @FormUrlEncoded
    @POST("pengguna/loginEmail")
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ):Call<PenggunaModel>



    /**
     * ENDPOINT TABEL KULINER
     */
    @FormUrlEncoded
    @POST("kuliner/select")
    fun selectKuliner(
        @Field("id") id :String
    ): Call<List<KulinerModel>>

    @FormUrlEncoded
    @POST("kuliner/insert")
    fun insertKuliner(
        @Field("nama") nama :String,
        @Field("toko") toko :String,
        @Field("harga") harga :String,
        @Field("deskripsi") deskripsi :String,
        @Field("url_gambar") url_gambar :String,
        @Field("url_map") url_map :String,
    ): Call<KulinerModel>

    @FormUrlEncoded
    @POST("kuliner/update")
    fun updateKuliner(
        @Field("id") id :String,
        @Field("nama") nama :String,
        @Field("toko") toko :String,
        @Field("harga") harga :String,
        @Field("deskripsi") deskripsi :String,
        @Field("url_gambar") url_gambar :String,
        @Field("url_map") url_map :String,
    ): Call<KulinerModel>

    @FormUrlEncoded
    @POST("kuliner/delete")
    fun deleteKuliner(
        @Field("id") id :String,
    ): Call<KulinerModel>
}