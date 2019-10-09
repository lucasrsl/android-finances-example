package com.example.fincancesk.extension

import java.text.SimpleDateFormat
import java.util.Calendar


fun Calendar.brazilFormatter() : String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(this.time)
}