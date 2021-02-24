package com.baqynra.withbaqyand.barbercut

import android.content.Context
import android.content.SharedPreferences

class Preferences {

    var APP_NAME = "BARBERCUT"

    private var log_status = "log_status"
    private var token_type = "token_type"
    private var expires = "expires"
    private var no_bukti = "no_bukti"
    private var nik = "nik"
    private var nama = "nama"
    private var alamat = "kode_lokasi"
    private var token = "token"
    private var noHp = "no_hp"
    private var kodePP = "kodepp"

    var sp: SharedPreferences? = null
    var spEditor: SharedPreferences.Editor? = null

    fun setPreferences(context: Context) {
        sp = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        spEditor = sp?.edit()
    }

    fun preferencesLogout() {
        spEditor!!.clear()
        spEditor!!.commit()
    }

    fun saveLogStatus(value: Boolean) {
        spEditor!!.putBoolean(log_status, value)
        spEditor!!.commit()
    }

    fun saveToken(value: String?) {
        spEditor!!.putString(token, value)
        spEditor!!.commit()
    }

    fun saveTokenType(value: String?) {
        spEditor!!.putString(token_type, value)
        spEditor!!.commit()
    }

    fun saveExpires(value: String?) {
        spEditor!!.putString(expires, value)
        spEditor!!.commit()
    }

    fun saveNoHp(value: String?) {
        spEditor!!.putString(noHp, value)
        spEditor!!.commit()
    }
    fun savenik(value: String?){
        spEditor!!.putString(nik, value)
        spEditor!!.commit()
    }

    fun savename(value: String?){
        spEditor!!.putString(nama, value)
        spEditor!!.commit()
    }
    fun savelock(value: String?){
        spEditor!!.putString(alamat,value)
        spEditor!!.commit()
    }


    fun getLogStatus(): Boolean {
        return sp!!.getBoolean(log_status, false)
    }

    fun getExpires(): String? {
        return sp!!.getString(expires, "N/A")
    }

    fun getTokenType(): String? {
        return sp!!.getString(token_type, "N/A")
    }

    fun getToken(): String? {
        return sp!!.getString(token, "N/A")
    }

    fun getNoHp(): String? {
        return sp!!.getString(noHp, "N/A")
    }

    fun getName(): String? {
        return sp!!.getString(nama, "N/A")
    }

    fun getNik() : String? {
        return sp!!.getString(nik, "N/A")
    }

    fun getLock() : String? {
        return sp!!.getString(alamat, "N/A")
    }

}