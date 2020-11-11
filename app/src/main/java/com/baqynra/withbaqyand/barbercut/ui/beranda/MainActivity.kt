package com.baqynra.withbaqyand.barbercut.ui.beranda

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baqynra.withbaqyand.barbercut.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out
        ).replace(R.id.frame_layout, PendapatanFragment()).commit()
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_beranda -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                    ).replace(R.id.frame_layout, PendapatanFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_kartu -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.frame_layout, NilaiTransaksiFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Yakin ingin keluar?")
        builder.setCancelable(true)
        builder.setPositiveButton("Ya") { _, _ -> super.onBackPressed() }
        builder.setNegativeButton("Tidak") { _, _ ->

        }
        val dialog = builder.create()
        dialog.show()
    }
}