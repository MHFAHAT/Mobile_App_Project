package com.example.messmanagement.data.repository

import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.Payment

class PaymentRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllPayments(): List<Payment> {

        val paymentList = mutableListOf<Payment>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT
                payment_id,
                user_id,
                month_year,
                meal_count,
                meal_rate,
                total_amount,
                status,
                paid_date
            FROM Payments
            ORDER BY month_year DESC, payment_id DESC
            """.trimIndent(),
            null
        )

        while (cursor.moveToNext()) {

            val payment = Payment(
                paymentId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("payment_id")
                ),
                userId = cursor.getString(
                    cursor.getColumnIndexOrThrow("user_id")
                ),
                monthYear = cursor.getString(
                    cursor.getColumnIndexOrThrow("month_year")
                ),
                mealCount = cursor.getInt(
                    cursor.getColumnIndexOrThrow("meal_count")
                ),
                mealRate = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("meal_rate")
                ),
                totalAmount = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("total_amount")
                ),
                status = cursor.getString(
                    cursor.getColumnIndexOrThrow("status")
                ),
                paidDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("paid_date")
                )
            )

            paymentList.add(payment)
        }

        cursor.close()

        return paymentList
    }
    fun getPaymentsByUser(userId: String): List<Payment> {

        val paymentList = mutableListOf<Payment>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT
            payment_id,
            user_id,
            month_year,
            meal_count,
            meal_rate,
            total_amount,
            status,
            paid_date
        FROM Payments
        WHERE user_id = ?
        ORDER BY month_year DESC, payment_id DESC
        """.trimIndent(),
            arrayOf(userId)
        )

        while (cursor.moveToNext()) {

            val payment = Payment(
                paymentId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("payment_id")
                ),
                userId = cursor.getString(
                    cursor.getColumnIndexOrThrow("user_id")
                ),
                monthYear = cursor.getString(
                    cursor.getColumnIndexOrThrow("month_year")
                ),
                mealCount = cursor.getInt(
                    cursor.getColumnIndexOrThrow("meal_count")
                ),
                mealRate = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("meal_rate")
                ),
                totalAmount = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("total_amount")
                ),
                status = cursor.getString(
                    cursor.getColumnIndexOrThrow("status")
                ),
                paidDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("paid_date")
                )
            )

            paymentList.add(payment)
        }

        cursor.close()

        return paymentList
    }
    fun insertPayment(
        userId: String,
        monthYear: String,
        mealCount: Int,
        mealRate: Double,
        totalAmount: Double
    ): Boolean {

        val db = dbHelper.writableDatabase

        val values = android.content.ContentValues().apply {
            put("user_id", userId)
            put("month_year", monthYear)
            put("meal_count", mealCount)
            put("meal_rate", mealRate)
            put("total_amount", totalAmount)
            put("status", "Unpaid")
            putNull("paid_date")
        }

        val result = db.insert(
            "Payments",
            null,
            values
        )

        return result != -1L
    }
}