package com.example.fincancesk.delegate

import com.example.fincancesk.model.Transaction

interface TransacrionDelegate {

    fun delegate(transaction: Transaction) {}
}