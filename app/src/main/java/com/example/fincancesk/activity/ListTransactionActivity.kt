package com.example.fincancesk.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fincancesk.R
import com.example.fincancesk.adapter.ListTransactionAdapter
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val list = listOf(
            Transaction(
                value = BigDecimal(20.5),
                type = Type.EXPENSE,
                category = "Comida"
            ),
            Transaction(
                value = BigDecimal(150.0),
                type = Type.EXPENSE
            ),
            Transaction(
                value = BigDecimal(200.0),
                category = "Freela",
                type = Type.REVENUE
            )
        )

        val transactionAdapter = ListTransactionAdapter(
            list,
            this
        )

        lista_transacoes_listview.adapter = transactionAdapter
    }
}
