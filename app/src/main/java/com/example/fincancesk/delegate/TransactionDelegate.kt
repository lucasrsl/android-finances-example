package com.example.fincancesk.delegate

import com.example.fincancesk.model.Transaction

interface TransactionDelegate {

    fun delegate(transaction: Transaction) {}
}