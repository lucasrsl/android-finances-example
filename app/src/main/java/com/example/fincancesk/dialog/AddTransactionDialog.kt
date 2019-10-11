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
import com.example.fincancesk.delegate.TransacrionDelegate
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
    fun configDialog(transacrionDelegate: TransacrionDelegate) {

        configDateField()

        configCategoryField()

        configForm(transacrionDelegate)
    }

    private fun configForm(transacrionDelegate: TransacrionDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_despesa)
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
                    type = Type.EXPENSE,
                    value = value,
                    date = date,
                    category = categoryText
                )

                transacrionDelegate.delegate(createdTransaction)
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

    private fun configCategoryField() {
        val adapter = ArrayAdapter
            .createFromResource(
                context,
                R.array.categorias_de_despesa,
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