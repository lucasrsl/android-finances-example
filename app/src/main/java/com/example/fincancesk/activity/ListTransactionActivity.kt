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
import com.example.fincancesk.dialog.UpdateTransactionDialog
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListTransactionActivity : AppCompatActivity() {

    private var transactions: MutableList<Transaction> = mutableListOf()
    private var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)


        view = window.decorView as ViewGroup
        configTotal()
        configList()
        configFab()

    }

    private fun configFab() {
        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                callAddDialog(Type.EXPENSE)
            }
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                callAddDialog(Type.REVENUE)
            }
    }

    private fun callAddDialog(type: Type) {
        AddTransactionDialog(view as ViewGroup, this)
            .call(type, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    add(transaction)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun add(transaction: Transaction) {
        transactions.add(transaction)
        updateTransactions()
    }

    private fun updateTransactions() {
        configTotal()
        configList()
    }

    private fun configTotal() {
        val totalView = TotalView(this, transactions, view)

        totalView.updateTotal()
    }

    private fun configList() {
        val adapterTransactions = ListTransactionAdapter(transactions, this)
        with(lista_transacoes_listview) {
            adapter = adapterTransactions
            setOnItemClickListener { _, _, pos, _ ->
                val transaction = transactions[pos]
                callUpdateDialog(pos, transaction)
            }
        }
    }

    private fun callUpdateDialog(
        pos: Int,
        transaction: Transaction
    ) {
        UpdateTransactionDialog(view as ViewGroup, this)
            .call(transaction, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    update(pos, transaction)
                }
            })
    }

    private fun update(pos: Int, transaction: Transaction) {
        transactions[pos] = transaction
        updateTransactions()
    }

}
