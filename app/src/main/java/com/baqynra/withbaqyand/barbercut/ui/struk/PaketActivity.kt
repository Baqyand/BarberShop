package com.baqynra.withbaqyand.barbercut.ui.struk

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.baqynra.withbaqyand.barbercut.Library
import com.baqynra.withbaqyand.barbercut.Preferences
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.adapter.PaketAdapter
import com.baqynra.withbaqyand.barbercut.apihelper.ApiClient
import com.baqynra.withbaqyand.barbercut.model.Datalocal
import com.baqynra.withbaqyand.barbercut.model.Paketpass
import com.baqynra.withbaqyand.barbercut.ui.login.LoginActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import kotlinx.android.synthetic.main.activity_paket.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class PaketActivity : AppCompatActivity(), PrintingCallback {

    companion object{
        val dataarr = mutableListOf<Datalocal>()
        var param = 0
        var harga = 0
        var totalnya = 0
        var nik: String? = null
        var nama: String? = null
        var lokasi: String? = null
        var no_hp: String? = null
        val preferences = Preferences()
        val library = Library()
        var nocust:String? = null
        var kode: String? = null
        private lateinit var adapter : PaketAdapter
        internal var printing: Printing? = null
        var namapaket:String? = null
        val tanggal: String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        Printooth.init(this)
        preferences.setPreferences(this)

        nik = preferences.getNik()
        nama = preferences.getName()
        lokasi = preferences.getLock()
        no_hp = preferences.getNoHp()


//        val getShared = getSharedPreferences("BARBERCUT", MODE_PRIVATE)



        
        adapter = PaketAdapter(this)
        rv_paket.layoutManager  = GridLayoutManager(this, 2)
        rv_paket.adapter = adapter
        refresh.setOnRefreshListener { getdata() }
//        getbukti(tanggal)
        getNoCust()
        getdata()
        initview()
//        inivisible()
        total.text = library.toRupiah(totalnya)
    }
    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessegeReceiver,
            IntentFilter("struk")
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessegeReceiver)
    }

    private val mMessegeReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("total")){
                //TODO:  data array++ || --, pembanding add, upload api,
                totalnya = intent.getIntExtra("total", 0)
                namapaket = intent.getStringExtra("nama_paket")!!
                kode = intent.getStringExtra("kode")!!
                harga = intent.getIntExtra("harga", 0)
                param = intent.getIntExtra("unit", 0)

                if (intent.getStringExtra("param") == "add"){
                    dataarr.add(Datalocal(namapaket, kode, harga))
                }
                else{
                    dataarr.removeAt(0)
                }

                Toast.makeText(this@PaketActivity, "struk trigger", Toast.LENGTH_SHORT).show()
                total.text = library.toRupiah(totalnya)
            }
        }
    }
    private fun initview() {
        if ( printing != null){
            printing!!.printingCallback = this
        }

        back.setOnClickListener {
            onBackPressed()
        }
        scan_bluetooth.setOnClickListener {
                if(Printooth.hasPairedPrinter()){
                    Printooth.removeCurrentPrinter()
                }
                else{
                    Toast.makeText(this, "Harap tunggu sampai scan selesai", Toast.LENGTH_LONG).show()
                    startActivityForResult(
                        Intent(this@PaketActivity, ScanningActivity::class.java),
                        ScanningActivity.SCANNING_FOR_PRINTER
                    )
                    changePairAndUnpair()
                }
        }
        buttoncetak.setOnClickListener {
            postdata(nik!!, nocust!!, nama!!, lokasi!!, no_hp!!, totalnya, 0, kode!!, tanggal, "TESBRB01")
            Log.e("cek", "$nik + $nama + $lokasi + $no_hp , $nocust, $totalnya, 0, $kode, $tanggal, $namapaket")
//            printText()
        }
    }

    private fun changePairAndUnpair() {
        if (Printooth.hasPairedPrinter()){
            scan_bluetooth.text = "Terhubung ${Printooth.getPairedPrinter()?.name}"
        }else{
            scan_bluetooth.text = "Tidak Terhubung"
        }
    }


    private fun printText() {
        val printables = ArrayList<Printable>()
        printables.add(RawPrintable.Builder(byteArrayOf(27, 100, 2)).build())
//        byteArrayOf(27,100,4)
        //add text
//        printables.add(TextPrintable.Builder()
//            .setText("Hello Gaiss : Ini Struknya")
//            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
//            .setNewLinesAfter(1)
//            .build())

        //custom
        printables.add(
            TextPrintable.Builder()
                .setText("Selamat Datang")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )

        printables.add(
            TextPrintable.Builder()
                .setText("di")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText("Barber Cut ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText("==============================")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(Date())
        printables.add(
            TextPrintable.Builder()
                .setText(currentDate)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
//        printables.add(
//            TextPrintable.Builder()
//                .setText(" ${getbukti(currentDate)} $data")
//                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
//                .setNewLinesAfter(1)
//                .build())
        printables.add(
            TextPrintable.Builder()
                .setText("Nama Paket :  ")
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setNewLinesAfter(1)
                .build()
        )

        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )

        for (i in 0 until dataarr.size - 1){
            val current = dataarr [i]
            var j = i + 1
            while (j <= dataarr.size - 1){
                val compare = dataarr[j]
                if (current.nama_paket!!.equals(compare.nama_paket) && current.kode!!.equals(compare.kode)){
                    current.harga = current.harga + compare.harga
                    dataarr.remove(compare)
                    j--
                }
                j++
            }
        }

        for (data in dataarr) {
            printables.add(
                TextPrintable.Builder()
                    .setText("${data.nama_paket}  Jumlah $param")
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setNewLinesAfter(1)
                    .build()
            )
            printables.add(
                TextPrintable.Builder()
                    .setText(" ")
                    .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                    .setNewLinesAfter(1)
                    .build()
            )
            printables.add(
                TextPrintable.Builder()
                    .setText("${library.toRupiah(data.harga)}")
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
//              .setFontSize(DefaultPrinter.)
                    .setNewLinesAfter(1)
                    .build()
            )
        }

        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText("==============================")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )

            printables.add(
                TextPrintable.Builder()
                    .setText("Total :               ${library.toRupiah(totalnya)}")
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setNewLinesAfter(1)
                    .build()
            )

        printables.add(
            TextPrintable.Builder()
                .setText("\n ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build()
        )

        printing?.print(printables)
    }


    override fun connectingWithPrinter() {
        Toast.makeText(this, "Connecting ", Toast.LENGTH_SHORT).show()
    }

    override fun connectionFailed(error: String) {
        Toast.makeText(this, "Failed : $error ", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String) {
        Toast.makeText(this, "Error : $error", Toast.LENGTH_SHORT).show()
    }

    override fun onMessage(message: String) {
        Toast.makeText(this, "Message : $message ", Toast.LENGTH_SHORT).show()
    }

    override fun printingOrderSentSuccessfully() {
        Toast.makeText(this, "Sent to Printer ", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@PaketActivity, StrukActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK){
            initPrinting()
            changePairAndUnpair()
        }
    }

    private fun initPrinting() {
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        if (printing!= null)
            printing!!.printingCallback = this

    }
    fun getdata(){
        val apiClient = ApiClient().getApiService(this)
        apiClient?.paket()?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val gson = Gson()
//                            val myobj: JSONArray? = obj.optJSONArray("data")
                            val type: Type = object : TypeToken<MutableList<Paketpass>>() {}.type
                            val data: MutableList<Paketpass> = gson.fromJson(
                                obj.optString("data"), type
                            )
                            adapter.initData(data)

//                            dataAdapter.clearData()
//                                        obj.optString("2020-10-12"), obj.optString("TESBRB01")
                        } catch (e: Exception) {
                            e.stackTrace
                        }
                    }

                } else if (response.code() == 401) {
                    startActivity(Intent(this@PaketActivity, LoginActivity::class.java))
                    Toast.makeText(
                        this@PaketActivity,
                        "Alangkah Baiknya Login dulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@PaketActivity, "Ada Kesalahan", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getNoCust(){
        val apiClient = ApiClient().getApiService(this)
        apiClient?.cust()?.enqueue(object  : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    if(response.body() != null){
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val myobj = JSONArray(obj.optString("data"))
                            for (counter in 0 until myobj.length()){
                                val jsonObject = myobj.getJSONObject(counter)
                                nocust = jsonObject.optString("kode_cust")
                            }

                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@PaketActivity, "Ada permasalahan ", Toast.LENGTH_SHORT).show()
            }

        })
    }


// mendapatkan bukti
    fun getbukti(tanggal: String){
        val apidata = ApiClient().getApiService(this)
        apidata?.nobukti(tanggal)?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val myobj = obj.optJSONObject("success")
                            val data = myobj.optString("data")

//                            nocust = data


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@PaketActivity, "Ada permasalahan ", Toast.LENGTH_SHORT).show()
            }

        })
    }
// post ke api
    fun postdata(
    nik_user: String,
    kode_cust: String,
    nama: String,
    alamat: String,
    no_hp: String,
    nilai: Int,
    diskon: Int,
    kode_paket: String,
    tanggal: String,
    kode_barber:String
){
        val apiClient = ApiClient().getApiService(this)
        apiClient?.kunj(nik_user, kode_cust, nama, alamat, no_hp, kode_paket, nilai, diskon,tanggal, kode_barber )?.enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            try {
                                val obj = JSONObject(response.body()!!.string())
                            } catch (e: Exception) {
                                e.stackTrace
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //TODO("Not yet implemented")
                }

            })
    }
}
