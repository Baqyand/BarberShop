package com.baqynra.withbaqyand.barbercut.apihelper

import android.content.Context

class ApiDuo {
    val BASE_TRANS = "https://api.simkug.com/api/barber-trans/"

    fun getApiTrans(context: Context): Api?{
        return RetrofitClient.getClient("https://api.simkug.com/api/barber-trans/",context)?.create(Api::class.java)
    }
}