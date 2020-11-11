package com.baqynra.withbaqyand.barbercut.apihelper

import android.content.Context
import retrofit2.create

class ApiClient {
    val BASE_API =  "https://api.simkug.com/api/"

    fun getApiService(context: Context): Api?{
        return RetrofitClient.getClient(BASE_API,context)?.create(Api::class.java)
    }

}
