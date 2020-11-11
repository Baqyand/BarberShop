package com.baqynra.withbaqyand.barbercut.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.baqynra.withbaqyand.barbercut.Preferences
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.apihelper.ApiClient
import com.baqynra.withbaqyand.barbercut.ui.beranda.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    var preferences = Preferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferences.setPreferences(this)


        buttonLogin.setOnClickListener {
            login(et_username.text.toString(),et_password.text.toString())
        }


    }
    fun login (id: String, password:String){
        val apiClient = ApiClient().getApiService(this)
        apiClient?.login(id,password)?.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    if (response.body() != null){
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            preferences.saveToken(obj.optString("token"))
                            preferences.saveExpires(obj.optString("expires"))
                            preferences.saveTokenType(obj.optString("token_type"))
                            preferences.saveNoHp(id)
                            preferences.saveLogStatus(true)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finishAffinity()

                        }catch (e: Exception){
                            e.printStackTrace()
                            Toast.makeText(this@LoginActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@LoginActivity, "Username/Password masih kosong", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    Toast.makeText(this@LoginActivity, "Username/Password salah", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@LoginActivity, "Token Invalid", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404 || response.code() == 405){
                    Toast.makeText(this@LoginActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }

        })
    }




}