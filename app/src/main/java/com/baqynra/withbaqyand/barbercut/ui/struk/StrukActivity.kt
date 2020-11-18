package com.baqynra.withbaqyand.barbercut.ui.struk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.ui.beranda.MainActivity
import kotlinx.android.synthetic.main.fragment_struk.*

class StrukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_struk)

        btn_selesaistruk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}