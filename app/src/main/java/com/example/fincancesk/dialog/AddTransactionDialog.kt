package com.example.fincancesk.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fincancesk.R
import com.example.fincancesk.delegate.TransactionDelegate
import com.example.fincancesk.extension.brazilFormatter
import com.example.fincancesk.extension.convertToCalendar
import com.example.fincancesk.model.Transaction
import com.example.fincancesk.model.Type
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AddTransactionDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val createdView = createLayout()
    fun configDialog(type: Type, transactionDelegate: TransactionDelegate) {

        configDateField()

        configCategoryField(type)

        configForm(type, transactionDelegate)
    }

    private fun configForm(type: Type, transactionDelegate: TransactionDelegate) {

        val title = if (type == Type.REVENUE) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton(
                "Adicionar"
            ) { _, _ ->
                val valueText = createdView
                    .form_transacao_valor.text.toString()
                val dateText = createdView
                    .form_transacao_data.text.toString()
                val categoryText = createdView
                    .form_transacao_categoria.selectedItem.toString()

                val value = convertValueField(valueText)

                val date = dateText.convertToCalendar()

                val createdTransaction = Transaction(
                    type = type,
                    value = value,
                    date = date,
                    category = categoryText
                )

                transactionDelegate.delegate(createdTransaction)
            }

            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun convertValueField(valueText: String): BigDecimal {
        return try {
            BigDecimal(valueText)
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                context,
                "Falha na conversão de texto",
                Toast.LENGTH_LONG
            )
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configCategoryField(type: Type) {
        val categories = if (type == Type.REVENUE) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categories,
                android.R.layout.simple_spinner_dropdown_item
            )

        createdView
            .form_transacao_categoria.adapter = adapter
    }

    private fun configDateField() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        createdView.form_transacao_data
            .setText(today.brazilFormatter())

        createdView.form_transacao_data.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val selectedDate: Calendar = Calendar.getInstance()
                    selectedDate.set(year, month, day)
                    createdView.form_transacao_data
                        .setText(selectedDate.brazilFormatter())
                },
                year, month, day
            )
                .show()
        }
    }

    private fun createLayout(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.form_transacao,
                viewGroup,
                false
            )
    }
}