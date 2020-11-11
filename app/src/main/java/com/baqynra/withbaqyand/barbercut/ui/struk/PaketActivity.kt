package com.baqynra.withbaqyand.barbercut.ui.struk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.baqynra.withbaqyand.barbercut.Library
import com.baqynra.withbaqyand.barbercut.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_paket.*

class PaketActivity : AppCompatActivity() {
    companion object{
        var unit = 0
        var totalnya = 0.0
        val library = Library()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        back.setOnClickListener {
            onBackPressed()
        }
        buttoncetak.setOnClickListener {
            val intent = Intent(this,StrukActivity::class.java)
            startActivity(intent)
        }

        btn1.setOnClickListener{
            if(collapsible.visibility== View.GONE){
                collapsible.visibility= View.VISIBLE
            }else{
                collapsible.visibility= View.GONE
            }
        }
        btn2.setOnClickListener{
            if(a.visibility== View.GONE){
                a.visibility= View.VISIBLE
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
        }
        btn4.setOnClickListener{
            if(c.visibility== View.GONE){
                c.visibility= View.VISIBLE
            }else{
                c.visibility= View.GONE
            }
        }

        total.text = library.toRupiah(totalnya)

        plus.setOnClickListener {
            if(totalnya>99999999){
                Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                totalnya= 99999999.0
                total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
            }else{
                totalnya+=12000
                total.setText(library.toRupiah(totalnya))
            }
        }
        minus.setOnClickListener {
            if(totalnya>0)
                totalnya-=12000
            total.setText(library.toRupiah(totalnya))
        }
        ples.setOnClickListener {
            if(totalnya>99999999){
                Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                totalnya= 99999999.0
                total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
            }else{
                totalnya+=12000
                total.setText(library.toRupiah(totalnya))
            }
        }
        min.setOnClickListener {
            if(totalnya>0)
                totalnya-=12000

            total.setText(library.toRupiah(totalnya))
        }

        tambah.setOnClickListener {
            if(totalnya>99999999){
                Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                totalnya= 99999999.0
                total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
            }else{
                totalnya+=12000
                unit++
                total.setText(library.toRupiah(totalnya))
            }
        }
        kurang.setOnClickListener {
            if(totalnya>0)
                totalnya-=12000
//                unit--
            total.setText(library.toRupiah(totalnya))
        }

        positif.setOnClickListener {
            if(totalnya>99999999){
                Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
                totalnya= 99999999.0
                total.setText(library.toRupiah(totalnya))
//                total_bayar.setSelection(total_bayar.length())
            }else{
                totalnya+=12000
                unit++
                total.setText(library.toRupiah(totalnya))
            }
        }
        negatif.setOnClickListener {
            if(totalnya>0)
                totalnya-=12000
//
            total.setText(library.toRupiah(totalnya))
        }



    }
}