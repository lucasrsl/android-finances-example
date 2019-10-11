package com.example.fincancesk.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitTo(number: Int): String {
    if(this.length > number) {
        val FIRST_CHAR = 0
        return "${this.substring(FIRST_CHAR, number)}..."
    }
    return this
}

fun String.convertToCalendar(): Calendar {
    val formatBrazil = SimpleDateFormat("dd/MM/yyyy")
    val convertedDate = formatBrazil.parse(this)
    val date = Calendar.getInstance()
    date.time = convertedDate

    return date
}