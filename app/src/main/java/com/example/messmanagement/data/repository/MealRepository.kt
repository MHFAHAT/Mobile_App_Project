package com.example.messmanagement.data.repository

import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.Meal

class MealRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllMeals(): List<Meal> {

        val mealList = mutableListOf<Meal>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT * FROM Meals
            ORDER BY date DESC
            """.trimIndent(),
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val meal = Meal(

                    mealId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("meal_id")
                    ),

                    date = cursor.getString(
                        cursor.getColumnIndexOrThrow("date")
                    ),

                    lunchMenu = cursor.getString(
                        cursor.getColumnIndexOrThrow("lunch_menu")
                    ),

                    dinnerMenu = cursor.getString(
                        cursor.getColumnIndexOrThrow("dinner_menu")
                    )
                )

                mealList.add(meal)

            } while (cursor.moveToNext())
        }

        cursor.close()

        return mealList
    }

    fun addMeal(
        date: String,
        lunchMenu: String,
        dinnerMenu: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        val cursor = db.rawQuery(
            """
        SELECT meal_id FROM Meals
        WHERE date = ?
        """.trimIndent(),
            arrayOf(date)
        )

        val exists = cursor.count > 0

        cursor.close()

        if (exists) {
            return false
        }

        val values = android.content.ContentValues().apply {

            put("date", date)
            put("lunch_menu", lunchMenu)
            put("dinner_menu", dinnerMenu)
        }

        val result = db.insert(
            "Meals",
            null,
            values
        )

        return result != -1L
    }
}