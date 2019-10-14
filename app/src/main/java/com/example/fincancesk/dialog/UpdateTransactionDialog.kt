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
import java.util.Calendar

class UpdateTransactionDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val createdView = createLayout()
    private val valueField = createdView.form_transacao_valor
    private val categoryField = createdView.form_transacao_categoria
    private val dateField = createdView.form_transacao_data

    fun call(transaction: Transaction, transactionDelegate: TransactionDelegate) {

        val type = transaction.type


        configDateField()
        configCategoryField(type)
        configForm(type, transactionDelegate)

        valueField.setText(transaction.value.toString())
        dateField.setText(transaction.date.brazilFormatter())
        val categories = context.resources.getStringArray(categoriesBy(type))
        val indexCategory = categories.indexOf(transaction.category)
        categoryField.setSelection(indexCategory, true)
    }

    private fun configForm(type: Type, transactionDelegate: TransactionDelegate) {

        val title = titleBy(type)

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton(
                "Alterar"
            ) { _, _ ->
                val valueText = valueField.text.toString()
                val dateText = dateField.text.toString()
                val categoryText = categoryField.selectedItem.toString()

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

    private fun titleBy(type: Type): Int {
        if (type == Type.REVENUE) {
             return R.string.altera_receita
        }
        return R.string.altera_despesa
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
        val categories = categoriesBy(type)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categories,
                android.R.layout.simple_spinner_dropdown_item
            )

        categoryField.adapter = adapter
    }

    private fun categoriesBy(type: Type): Int {
        if (type == Type.REVENUE) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configDateField() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        dateField.setText(today.brazilFormatter())

        dateField.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val selectedDate: Calendar = Calendar.getInstance()
                    selectedDate.set(year, month, day)
                    dateField.setText(selectedDate.brazilFormatter())
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