package com.baqynra.withbaqyand.barbercut.ui.beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baqynra.withbaqyand.barbercut.Fungsi
import com.baqynra.withbaqyand.barbercut.Preferences
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.adapter.PendapatanAdapter
import com.baqynra.withbaqyand.barbercut.apihelper.ApiDuo
import com.baqynra.withbaqyand.barbercut.model.DataTransaksi
import com.baqynra.withbaqyand.barbercut.ui.closing.ClosingActivity
import com.baqynra.withbaqyand.barbercut.ui.login.LoginActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_nilai_transaksi.*
import kotlinx.android.synthetic.main.fragment_nilai_transaksi.rvpdp
import kotlinx.android.synthetic.main.fragment_nilai_transaksi.tv_closing
import kotlinx.android.synthetic.main.fragment_nilai_transaksi.view.*
import kotlinx.android.synthetic.main.fragment_pendapatan.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


class NilaiTransaksiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        var preferences = Preferences()
        private lateinit var myview : View
        private lateinit var functions: Fungsi
        private val dataArray= mutableListOf<DataTransaksi>()
        private lateinit var dataAdapter: PendapatanAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview =  inflater.inflate(R.layout.fragment_nilai_transaksi, container, false)

        dataAdapter = PendapatanAdapter(context!!)
        preferences.setPreferences(context!!)
        functions = Fungsi(context!!)
        myview.rvpdp.layoutManager = LinearLayoutManager(context)
        myview.rvpdp.adapter = dataAdapter

        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_closing.setOnClickListener {
            val intent = Intent(context!!, ClosingActivity::class.java)
            startActivity(intent)
        }
        val currentDate: String =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        segarkan.setOnRefreshListener { getdata(currentDate,"TESBRB01") }
        getdata(currentDate,"TESBRB01")

    }
    fun getdata(tanggal:String,kodeBarber:String){
        val apiClient = ApiDuo().getApiTrans(context!!)
        apiClient?.transaksi(tanggal,kodeBarber)?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val gson = Gson()
                            val myobj=obj.optJSONObject("success")
                            val type: Type =
                                object : TypeToken<MutableList<DataTransaksi>>() {}.type
                            val data: MutableList<DataTransaksi> = gson.fromJson(
                                myobj.optString("arrkunj"),type
//                                        obj.optString("2020-10-12"), obj.optString("TESBRB01")
                            )


                            dataAdapter.registerAdapterDataObserver(object :
                                RecyclerView.AdapterDataObserver() {
                                override fun onChanged() {
                                    super.onChanged()
                                    checkEmpty()
                                }

                                override fun onItemRangeInserted(
                                    positionStart: Int,
                                    itemCount: Int
                                ) {
                                    super.onItemRangeInserted(positionStart, itemCount)
                                    checkEmpty()
                                }

                                override fun onItemRangeRemoved(
                                    positionStart: Int,
                                    itemCount: Int
                                ) {
                                    super.onItemRangeRemoved(positionStart, itemCount)
                                    checkEmpty()
                                }

                                fun checkEmpty() {
                                    if (dataAdapter.itemCount == 0 || dataAdapter.itemCount < 1) {
                                        rvpdp.visibility = View.GONE
                                    } else {
                                        rvpdp.visibility = View.VISIBLE
                                    }

                                }
                            })
//                            dataAdapter.clearData()
                            dataAdapter.initData(data)
                            myview.segarkan.isRefreshing = false
                        } catch (e: Exception) {
                            e.printStackTrace()
                            myview.segarkan.isRefreshing = false
                        }
                    } else {
                        Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT)
                            .show()
                        myview.segarkan.isRefreshing = false
                    }
                } else if (response.code() == 422) {
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    myview.segarkan.isRefreshing = false
                } else if (response.code() == 401) {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    activity?.finish()
                    Toast.makeText(
                        context,
                        "Sesi telah berakhir, silahkan login kembali",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (response.code() == 403) {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                    myview.segarkan.isRefreshing = false
                } else if (response.code() == 404) {
                    Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    myview.segarkan.isRefreshing = false
                } else if (response.code() == 405) {
                    Toast.makeText(context, "Method Tidak diterima server", Toast.LENGTH_SHORT)
                        .show()
                    myview.segarkan.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                myview.segarkan.isRefreshing = false
            }

        })
    }


}