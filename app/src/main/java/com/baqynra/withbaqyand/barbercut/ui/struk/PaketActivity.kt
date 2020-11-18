package com.baqynra.withbaqyand.barbercut.ui.struk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.baqynra.withbaqyand.barbercut.Library
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.model.Paketpass
import com.baqynra.withbaqyand.barbercut.ui.beranda.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import kotlinx.android.synthetic.main.activity_paket.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaketActivity : AppCompatActivity(), PrintingCallback {
    var parameterbtn1=0
    var parameterbtn2=0
    var parameterbtn3=0
    var parameterbtn4=0
    companion object{
        val dataarr = arrayListOf<Paketpass>()
//        var harga = 0
        var totalnya = 0
        val library = Library()
        internal var printing: Printing? = null
//        var namapaket:String? = null

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        Printooth.init(this)


        initview()
        inivisible()
        total.text = library.toRupiah(totalnya)

//        dataarr.add(Paketpass("Cukur anak", 12000))
//        dataarr.add(Paketpass("Cukur Dewasa", 15000))
//        dataarr.add(Paketpass("Cuci Rambut", 3000))
//        dataarr.add(Paketpass("Cukur jenggot", 5000))
//        dataarr.size



    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        totalnya=0
        parameterbtn1=0
        parameterbtn2=0
        parameterbtn3=0
        parameterbtn4=0
        total.text = library.toRupiah(totalnya)
        dataarr.clear()
    }

    private fun inivisible() {
        btn1.setOnClickListener{
            if(collapsible.visibility== View.GONE){
                collapsible.visibility= View.VISIBLE
                plus.setOnClickListener {
                    if(totalnya>99999999){
                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                        totalnya = 99999999
                        total.setText(library.toRupiah(totalnya))

//                total_bayar.setSelection(total_bayar.length())
                    }else{
                        dataarr.add(Paketpass("Cukur anak", 12000))
                        totalnya +=12000
                        parameterbtn1+=1
                        total.setText(library.toRupiah(totalnya))
                    }
                }
                minus.setOnClickListener {
                    if(parameterbtn1 >=1 ){
                        if(totalnya>0)
                            totalnya-=12000
                        parameterbtn1 -=1
                        dataarr.removeLast()
                        total.setText(library.toRupiah(totalnya))
                    }

                }
            }else{
                collapsible.visibility= View.GONE
            }
        }

        btn2.setOnClickListener{
            if(a.visibility== View.GONE){
                a.visibility= View.VISIBLE
                ples.setOnClickListener {
                    if(totalnya>99999999){
                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                        totalnya= 99999999
                        total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
                    }else{
                        dataarr.add(Paketpass("Cukur Dewasa", 15000))
                        totalnya+=15000
                        parameterbtn2 +=1
                        total.setText(library.toRupiah(totalnya))
                    }
                }
                min.setOnClickListener {
                    if (parameterbtn2 >=1 ) {
                        if (totalnya > 0)
                            totalnya -= 15000
                        parameterbtn2 -= 1
                        dataarr.removeLast()
                        total.setText(library.toRupiah(totalnya))
                    }

                }
            }else{
                a.visibility= View.GONE
            }

        }
        btn3.setOnClickListener{
            if(b.visibility== View.GONE){
                b.visibility= View.VISIBLE
            }else{
                b.visibility= View.GONE
            }
            tambah.setOnClickListener {
                if(totalnya>99999999){
                    Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                    totalnya= 99999999
                    total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
                }else{
                    dataarr.add(Paketpass("Cuci Rambut", 3000))
                    totalnya += 3000
                    parameterbtn3 += 1
                    total.setText(library.toRupiah(totalnya))
                }
            }
            kurang.setOnClickListener {
                if (parameterbtn3 >=1 )
                if(totalnya>0)
                    totalnya-=3000
                    parameterbtn3 -= 1
//                unit--
                total.setText(library.toRupiah(totalnya))
            }
        }
        btn4.setOnClickListener{

            if(c.visibility== View.GONE){
                c.visibility= View.VISIBLE
            }else{
                c.visibility= View.GONE
            }
            positif.setOnClickListener {
                if(totalnya>99999999){
                    Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                    totalnya= 99999999
                    total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
                }else{
                    dataarr.add(Paketpass("Cukur jenggot", 5000))
                    totalnya+=5000
                    parameterbtn4 += 1
                    total.setText(library.toRupiah(totalnya))
                }
            }
            negatif.setOnClickListener {
                if (parameterbtn4 >= 1){
                    if(totalnya>0)
                        totalnya-=5000
                        parameterbtn4 -= 1
                    dataarr.removeLast()
                    total.setText(library.toRupiah(totalnya))
                }

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
                    Toast.makeText(this,"Harap tunggu sampai scan selesai",Toast.LENGTH_LONG).show()
                    startActivityForResult(Intent( this@PaketActivity, ScanningActivity::class.java),
                        ScanningActivity.SCANNING_FOR_PRINTER)
                    changePairAndUnpair()
                }
        }
        buttoncetak.setOnClickListener {
            printText()
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
        printables.add(RawPrintable.Builder(byteArrayOf(27,100,2)).build())
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
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText("di")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText("Barber Cut ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText("==============================")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(Date())
        printables.add(
            TextPrintable.Builder()
                .setText(currentDate)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText("Nama Paket :  ")
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setNewLinesAfter(1)
                .build())
        for(data in dataarr){

//            Log.e("paket", data.toString())
            printables.add(
                TextPrintable.Builder()
                    .setText(" ${data.paket} ")
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setNewLinesAfter(1)
                    .build())
            printables.add(
                TextPrintable.Builder()
                    .setText(" ${ library.toRupiah(data.harga)}")
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
//                    .setFontSize(DefaultPrinter.)
                    .setNewLinesAfter(1)
                    .build())
        }

        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText("==============================")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())

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
                .build())
        printables.add(
            TextPrintable.Builder()
                .setText(" ")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())

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
}

