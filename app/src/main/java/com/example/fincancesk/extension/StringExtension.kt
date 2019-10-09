package com.example.fincancesk.extension

fun String.limitTo(number: Int): String {
    if(this.length > number) {
        val FIRST_CHAR = 0
        return "${this.substring(FIRST_CHAR, number)}..."
    }
    return this
}