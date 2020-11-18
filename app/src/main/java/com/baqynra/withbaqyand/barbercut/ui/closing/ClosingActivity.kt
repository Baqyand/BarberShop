package com.baqynra.withbaqyand.barbercut.ui.closing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.ui.beranda.MainActivity
import kotlinx.android.synthetic.main.fragment_cetak_pendapatan.*
import kotlinx.android.synthetic.main.fragment_struk.*

class ClosingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cetak_pendapatan)

        btn1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }
}