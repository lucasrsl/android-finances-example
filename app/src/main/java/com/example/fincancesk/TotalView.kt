package com.example.fincancesk

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.fincancesk.extension.brazilFormatter
import com.example.fincancesk.model.Total
import com.example.fincancesk.model.Transaction
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class TotalView(private val context:Context,
                private val transactions: List<Transaction>,
                private val view: View) {

    private val total = Total(transactions)

    private val expenseColor = ContextCompat.getColor(context, R.color.despesa)

    private val revenueColor = ContextCompat.getColor(context, R.color.receita)

    fun updateTotal() {
        setTotalExpense()
        setTotalRevenue()
        setTotal()
    }

    private fun setTotalExpense() {
        with(view.resumo_card_despesa) {
            text = total.expense.brazilFormatter()
            setTextColor(expenseColor)
        }
    }

    private fun setTotalRevenue() {
        with(view.resumo_card_receita) {
            text = total.revenue.brazilFormatter()
            setTextColor(revenueColor)
        }
    }

    private fun setTotal() {
        val total: BigDecimal = total.total

        var totalColor: Int = colorByValue(total)

        with(view.resumo_card_total) {
            setTextColor(totalColor)
            text = total
                .brazilFormatter()
                .replace("-R\$ ", "R$ -")
        }
    }

    private fun colorByValue(total: BigDecimal): Int {
        if (total >= BigDecimal.ZERO) {
            return revenueColor
        }
        return  expenseColor
    }
}