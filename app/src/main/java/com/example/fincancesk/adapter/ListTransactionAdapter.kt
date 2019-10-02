package com.example.fincancesk.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fincancesk.R
import com.example.fincancesk.extension.brazilFormatter
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListTransactionAdapter(
    list: List<Transaction>,
    context: Context
) : BaseAdapter() {

    private val list = list
    private val context = context

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        val viewCreated = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)

        val transaction = list[pos]

        if (transaction.type == Type.REVENUE) {
            viewCreated.transacao_valor
                .setTextColor(ContextCompat.getColor(context, R.color.receita))

            viewCreated.transacao_icone
                .setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            viewCreated.transacao_valor
                .setTextColor(ContextCompat.getColor(context, R.color.despesa))

            viewCreated.transacao_icone
                .setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        }

        viewCreated.transacao_valor.text = transaction.value.brazilFormatter()
        viewCreated.transacao_categoria.text = transaction.category
        viewCreated.transacao_data.text = transaction.date.brazilFormatter()

        return viewCreated
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