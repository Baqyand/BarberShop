package com.baqynra.withbaqyand.barbercut.model

import com.google.gson.annotations.SerializedName

data class Paketpass(
    @field:SerializedName("kode_paket")
    val kode_paket : String? = null,
    @field:SerializedName("nama")
    val nama: String? = null,
    @field:SerializedName("nilai")
    val nilai:Int? = null,
    @field:SerializedName("flag_aktif")
    val flag_aktif: Int? = null
)

