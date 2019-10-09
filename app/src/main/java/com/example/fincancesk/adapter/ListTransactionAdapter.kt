package com.example.fincancesk.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fincancesk.R
import com.example.fincancesk.extension.brazilFormatter
import com.example.fincancesk.extension.limitTo
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListTransactionAdapter(
    private val list: List<Transaction>,
    private val context: Context
) : BaseAdapter() {

    private val CATEGORY_LIMIT = 14

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {

        val viewCreated = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)

        val transaction = list[pos]

        setValue(transaction, viewCreated)
        setIcon(transaction, viewCreated)
        setCategory(viewCreated, transaction)
        setDate(viewCreated, transaction)

        return viewCreated
    }

    private fun setDate(
        viewCreated: View,
        transaction: Transaction
    ) {
        viewCreated.transacao_data.text = transaction.date
            .brazilFormatter()
    }

    private fun setCategory(
        viewCreated: View,
        transaction: Transaction
    ) {
        viewCreated.transacao_categoria.text = transaction.category
            .limitTo(CATEGORY_LIMIT)
    }

    private fun setValue(
        transaction: Transaction,
        viewCreated: View
    ) {
        val color = colorBy(transaction.type)

        viewCreated.transacao_valor
            .setTextColor(color)

        viewCreated.transacao_valor.text = transaction.value
            .brazilFormatter()
    }

    private fun colorBy(type: Type): Int {
        return when (type) {
            Type.EXPENSE -> ContextCompat.getColor(context, R.color.despesa)
            Type.REVENUE -> ContextCompat.getColor(context, R.color.receita)
        }
    }

    private fun setIcon(
        transaction: Transaction,
        viewCreated: View
    ) {

        val icon = iconBy(transaction.type)
        viewCreated.transacao_icone
            .setBackgroundResource(icon)
    }

    private fun iconBy(type: Type): Int {
        return when (type) {
            Type.EXPENSE -> R.drawable.icone_transacao_item_despesa
            Type.REVENUE -> R.drawable.icone_transacao_item_receita
        }
    }

    override fun getItem(pos: Int): Transaction {
        return list[pos]
    }

    override fun getItemId(pos: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}