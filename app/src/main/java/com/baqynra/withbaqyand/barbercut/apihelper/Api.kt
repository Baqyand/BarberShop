package com.baqynra.withbaqyand.barbercut.apihelper

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api{
    @FormUrlEncoded
    @POST("barber-auth/login")
    fun login(
        @Field("nik") nik  : String?,
        @Field("password") password: String?,
    ):Call<ResponseBody>

    @GET("barber-trans/getKunj")
    fun transaksi(
        @Query("tanggal") tanggal:String,
        @Query("kode_barber") kode_barber:String
    ):Call<ResponseBody>

    @GET("barber-auth/profile")
    fun profile():Call<ResponseBody>
}