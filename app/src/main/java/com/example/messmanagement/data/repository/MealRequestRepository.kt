package com.example.messmanagement.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper

class MealRequestRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun saveMealRequest(
        userId: String,
        date: String,
        lunchStatus: Boolean,
        dinnerStatus: Boolean
    ): Boolean {

        val db = dbHelper.writableDatabase

        val cursor = db.rawQuery(
            """
            SELECT request_id FROM MealRequests
            WHERE user_id = ? AND date = ?
            """.trimIndent(),
            arrayOf(userId, date)
        )

        val exists = cursor.count > 0

        cursor.close()

        val values = ContentValues().apply {

            put(
                "lunch_status",
                if (lunchStatus) 1 else 0
            )

            put(
                "dinner_status",
                if (dinnerStatus) 1 else 0
            )
        }

        return if (exists) {

            val result = db.update(
                "MealRequests",
                values,
                "user_id = ? AND date = ?",
                arrayOf(userId, date)
            )

            result > 0

        } else {

            values.put("user_id", userId)

            values.put("date", date)

            val result = db.insert(
                "MealRequests",
                null,
                values
            )

            result != -1L
        }
    }

    fun getMealRequest(
        userId: String,
        date: String
    ): Pair<Boolean, Boolean>? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT lunch_status, dinner_status
        FROM MealRequests
        WHERE user_id = ? AND date = ?
        """.trimIndent(),
            arrayOf(userId, date)
        )

        var result: Pair<Boolean, Boolean>? = null

        if (cursor.moveToFirst()) {

            val lunchStatus =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow("lunch_status")
                ) == 1

            val dinnerStatus =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow("dinner_status")
                ) == 1

            result = Pair(lunchStatus, dinnerStatus)
        }

        cursor.close()

        return result
    }
}