package com.example.fincancesk.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.brazilFormatter(): String {
    val brazilFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))

    return brazilFormat.format(this)
}