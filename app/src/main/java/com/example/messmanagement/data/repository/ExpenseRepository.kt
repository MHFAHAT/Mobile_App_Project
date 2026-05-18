package com.example.messmanagement.data.repository

import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.Expense

class ExpenseRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllExpenses(): List<Expense> {

        val expenseList = mutableListOf<Expense>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT expense_id, date, category, amount, entered_by
            FROM Expenses
            ORDER BY date DESC, expense_id DESC
            """.trimIndent(),
            null
        )

        while (cursor.moveToNext()) {

            val expense = Expense(
                expenseId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("expense_id")
                ),
                date = cursor.getString(
                    cursor.getColumnIndexOrThrow("date")
                ),
                category = cursor.getString(
                    cursor.getColumnIndexOrThrow("category")
                ),
                amount = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("amount")
                ),
                enteredBy = cursor.getString(
                    cursor.getColumnIndexOrThrow("entered_by")
                )
            )

            expenseList.add(expense)
        }

        cursor.close()

        return expenseList
    }
    fun insertExpense(
        date: String,
        category: String,
        amount: Double,
        enteredBy: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        val values = android.content.ContentValues().apply {
            put("date", date)
            put("category", category)
            put("amount", amount)
            put("entered_by", enteredBy)
        }

        val result = db.insert(
            "Expenses",
            null,
            values
        )

        return result != -1L
    }
}