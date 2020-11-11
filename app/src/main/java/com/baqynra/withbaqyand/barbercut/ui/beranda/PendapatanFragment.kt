package com.baqynra.withbaqyand.barbercut.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baqynra.withbaqyand.barbercut.Fungsi
import com.baqynra.withbaqyand.barbercut.Library
import com.baqynra.withbaqyand.barbercut.Preferences
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.adapter.PendapatanAdapter
import com.baqynra.withbaqyand.barbercut.apihelper.ApiClient
import com.baqynra.withbaqyand.barbercut.apihelper.ApiDuo
import com.baqynra.withbaqyand.barbercut.model.DataTransaksi
import com.baqynra.withbaqyand.barbercut.ui.login.LoginActivity
import com.baqynra.withbaqyand.barbercut.ui.struk.PaketActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_pendapatan.*
import kotlinx.android.synthetic.main.fragment_pendapatan.view.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class PendapatanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        var preferences = Preferences()
        private lateinit var myview : View
        private lateinit var functions: Fungsi
        private val dataArray= mutableListOf<DataTransaksi>()
        private lateinit var dataAdapter: PendapatanAdapter
        var name: String? = null
        var barber:String? = null
        val library = Library()
        val tanggal:String? = null
        val kode_barber:String? = null
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myview = inflater.inflate(R.layout.fragment_pendapatan, container, false)
        dataAdapter = PendapatanAdapter(context!!)
        preferences.setPreferences(context!!)
        functions = Fungsi(context!!)
        myview.rvpdp.layoutManager = LinearLayoutManager(context)
        myview.rvpdp.adapter = dataAdapter

        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dataArray.clear()
//        dataArray.add(DataTransaksi("donasi","120193201","RP.40000"))
//        dataArray.add(DataTransaksi("laporan","2039102","RP.50000"))
//        dataArray.add(DataTransaksi("iuran","1231321","RP. 25000"))
//        dataArray.add(DataTransaksi("donasi","120193201","RP.40000"))
//        dataArray.add(DataTransaksi("laporan","2039102","RP.50000"))
//        dataArray.add(DataTransaksi("iuran","1231321","RP. 25000"))
//        dataArray.add(DataTransaksi("donasi","120193201","RP.40000"))
//        dataArray.add(DataTransaksi("laporan","2039102","RP.50000"))
//        dataArray.add(DataTransaksi("iuran","1231321","RP. 25000"))
//        dataAdapter.addData(dataArray)


        tv_closing.setOnClickListener {
            val nilaiTransaksiFragment = NilaiTransaksiFragment()
            getActivity()?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.frame_layout, nilaiTransaksiFragment, "findThisFragment")
                ?.addToBackStack(null)
                ?.commit()
        }
        card.setOnClickListener {
            val go = Intent(context, PaketActivity::class.java)
            startActivity(go)
        }
        refresh.setOnRefreshListener { getdata("2020-10-12","TESBRB01") }
        getdata("2020-10-12","TESBRB01")
        initdata()
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
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
                                        empty_view.visibility = View.VISIBLE
                                        rvpdp.visibility = View.GONE
                                    } else {
                                        rvpdp.visibility = View.VISIBLE
                                    }

                                }
                            })
//                            dataAdapter.clearData()
                            dataAdapter.initData(data)
                            myview.refresh.isRefreshing = false
                        } catch (e: Exception) {
                            e.printStackTrace()
                            myview.refresh.isRefreshing = false
                        }
                    } else {
                        Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT)
                            .show()
                        myview.refresh.isRefreshing = false
                    }
                } else if (response.code() == 422) {
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    myview.refresh.isRefreshing = false
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
                    myview.refresh.isRefreshing = false
                } else if (response.code() == 404) {
                    Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    myview.refresh.isRefreshing = false
                } else if (response.code() == 405) {
                    Toast.makeText(context, "Method Tidak diterima server", Toast.LENGTH_SHORT)
                        .show()
                    myview.refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                myview.refresh.isRefreshing = false
            }

        })
    }

    fun initdata(){
        val apiClient = ApiClient().getApiService(context!!)
        apiClient?.profile()?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val data = JSONArray(obj.optString("user"))
                            for (counter in 0 until data.length()) {
                                val jsonObject = data.getJSONObject(counter)
                                name = jsonObject.optString("nama")
                                barber = jsonObject.optString("nmlok")

                            }
                            tv_nama.text = name
                            tv_barber.text = barber
                        } catch (e: Exception) {
                            e.printStackTrace()

                            Toast.makeText(context, "Terjadi Permasalahan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }

        })
    }



//
}