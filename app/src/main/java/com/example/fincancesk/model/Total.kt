package com.example.fincancesk.model

import java.math.BigDecimal

class Total(private val transactions: List<Transaction>) {
    val revenue get() = sumBy(Type.REVENUE)

    val expense get() = sumBy(Type.EXPENSE)

    val total get() = revenue.subtract(expense)!!

    private fun sumBy(type: Type): BigDecimal {
        return BigDecimal(transactions
            .filter { it.type == type }
            .sumByDouble { it.value.toDouble() })
    }
}
