package com.example.fincancesk.activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fincancesk.R
import com.example.fincancesk.TotalView
import com.example.fincancesk.adapter.ListTransactionAdapter
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val list: List<Transaction> = transactionsExample()

        configTotal(list)

        configList(list)

        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                val view: View = window.decorView
                val createdView = LayoutInflater.from(this)
                    .inflate(
                        R.layout.form_transacao,
                        view as ViewGroup,
                        false)

                AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_despesa)
                    .setView(createdView)
                    .show()
            }
    }

    private fun configTotal(list: List<Transaction>) {
        val view: View = window.decorView

        val totalView = TotalView(this, list, view)

        totalView.updateTotal()
    }

    private fun configList(list: List<Transaction>) {
        lista_transacoes_listview.adapter = ListTransactionAdapter(list, this)
    }

    private fun transactionsExample(): List<Transaction> {
        return listOf(
            Transaction(
                value = BigDecimal(20.5),
                type = Type.EXPENSE,
                category = "Comida"
            ),
            Transaction(
                value = BigDecimal(100.0),
                type = Type.EXPENSE
            ),
            Transaction(
                value = BigDecimal(200.0),
                category = "Freela",
                type = Type.REVENUE
            )
        )
    }
}
