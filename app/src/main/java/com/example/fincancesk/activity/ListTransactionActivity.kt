package com.example.fincancesk.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.fincancesk.R
import com.example.fincancesk.TotalView
import com.example.fincancesk.adapter.ListTransactionAdapter
import com.example.fincancesk.delegate.TransactionDelegate
import com.example.fincancesk.dialog.AddTransactionDialog
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListTransactionActivity : AppCompatActivity() {

    private var transactions: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)


        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                AddTransactionDialog(window.decorView as ViewGroup, this)
                    .configDialog(Type.EXPENSE, object : TransactionDelegate {
                        override fun delegate(transaction: Transaction) {
                            updateTransactions(transaction)
                            lista_transacoes_adiciona_menu.close(true)
                        }
                    })
            }
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                AddTransactionDialog(window.decorView as ViewGroup, this)
                    .configDialog(Type.REVENUE, object : TransactionDelegate {
                        override fun delegate(transaction: Transaction) {
                            updateTransactions(transaction)
                            lista_transacoes_adiciona_menu.close(true)
                        }
                    })
            }

    }

    private fun updateTransactions(transaction: Transaction) {
        transactions.add(transaction)
        configTotal()
        configList()
    }

    private fun configTotal() {
        val view: View = window.decorView

        val totalView = TotalView(this, transactions, view)

        totalView.updateTotal()
    }

    private fun configList() {
        lista_transacoes_listview.adapter = ListTransactionAdapter(transactions, this)
    }

}
