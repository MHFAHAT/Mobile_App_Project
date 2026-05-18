package com.example.messmanagement.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {

        const val DATABASE_NAME = "mess_management.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USERS = "Users"
        const val TABLE_MEALS = "Meals"
        const val TABLE_MEAL_REQUESTS = "MealRequests"
        const val TABLE_EXPENSES = "Expenses"
        const val TABLE_PAYMENTS = "Payments"
        const val TABLE_NOTICES = "Notices"
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("PRAGMA foreign_keys=ON")

        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                uid TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                role TEXT NOT NULL CHECK(role IN ('Admin', 'Staff', 'Resident')),
                phone TEXT UNIQUE,
                password TEXT NOT NULL,
                room_number TEXT,
                created_at TEXT DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()

        val createMealsTable = """
            CREATE TABLE $TABLE_MEALS (
                meal_id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL UNIQUE,
                lunch_menu TEXT,
                dinner_menu TEXT
            )
        """.trimIndent()

        val createMealRequestsTable = """
            CREATE TABLE $TABLE_MEAL_REQUESTS (
                request_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT NOT NULL,
                date TEXT NOT NULL,
                lunch_status INTEGER DEFAULT 0 CHECK(lunch_status IN (0,1)),
                dinner_status INTEGER DEFAULT 0 CHECK(dinner_status IN (0,1)),
                FOREIGN KEY(user_id) REFERENCES Users(uid) ON DELETE CASCADE,
                UNIQUE(user_id, date)
            )
        """.trimIndent()

        val createExpensesTable = """
            CREATE TABLE $TABLE_EXPENSES (
                expense_id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL,
                category TEXT NOT NULL,
                amount REAL NOT NULL,
                entered_by TEXT,
                FOREIGN KEY(entered_by) REFERENCES Users(uid)
            )
        """.trimIndent()

        val createPaymentsTable = """
            CREATE TABLE $TABLE_PAYMENTS (
                payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT NOT NULL,
                month_year TEXT NOT NULL,
                meal_count INTEGER DEFAULT 0,
                meal_rate REAL DEFAULT 0,
                total_amount REAL NOT NULL,
                status TEXT NOT NULL DEFAULT 'Unpaid'
                    CHECK(status IN ('Paid', 'Unpaid')),
                paid_date TEXT,
                FOREIGN KEY(user_id) REFERENCES Users(uid)
            )
        """.trimIndent()

        val createNoticesTable = """
            CREATE TABLE $TABLE_NOTICES (
                notice_id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                message TEXT NOT NULL,
                timestamp TEXT DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createMealsTable)
        db.execSQL(createMealRequestsTable)
        db.execSQL(createExpensesTable)
        db.execSQL(createPaymentsTable)
        db.execSQL(createNoticesTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEAL_REQUESTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PAYMENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEALS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTICES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")

        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }
}