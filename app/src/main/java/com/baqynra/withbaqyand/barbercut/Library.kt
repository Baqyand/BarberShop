package com.baqynra.withbaqyand.barbercut

import java.text.NumberFormat
import java.util.*

class Library {
    fun toRupiah(nilai: Int): String? {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)

        return formatRupiah.format(nilai)
    }
}