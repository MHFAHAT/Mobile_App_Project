package com.example.messmanagement.data.repository

import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper

class DashboardRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getTotalMeals(): Int {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM Meals",
            null
        )

        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()

        return count
    }

    fun getTotalExpenses(): Double {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT SUM(amount) FROM Expenses",
            null
        )

        var total = 0.0

        if (cursor.moveToFirst()) {

            total = cursor.getDouble(0)
        }

        cursor.close()

        return total
    }

    fun getTotalNotices(): Int {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM Notices",
            null
        )

        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()

        return count
    }

    fun getPendingPayments(): Int {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT COUNT(*)
            FROM Payments
            WHERE status = 'Unpaid'
            """.trimIndent(),
            null
        )

        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()

        return count
    }
}