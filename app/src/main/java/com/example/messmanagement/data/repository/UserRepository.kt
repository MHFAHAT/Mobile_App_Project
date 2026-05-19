package com.example.messmanagement.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.User
import com.example.messmanagement.utils.HashUtils
import com.example.messmanagement.utils.IdGenerator

class UserRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun registerUser(
        name: String,
        phone: String,
        roomNumber: String,
        password: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        if (isPhoneExists(phone)) {
            return false
        }

        val userCount = getUserCount()

        val userId = IdGenerator.generateUserId(userCount)

        val hashedPassword = HashUtils.hashPassword(password)

        val values = ContentValues().apply {

            put("uid", userId)
            put("name", name)
            put("role", "Resident")
            put("phone", phone)
            put("password", hashedPassword)
            put("room_number", roomNumber)
        }

        val result = db.insert(
            DatabaseHelper.TABLE_USERS,
            null,
            values
        )


        return result != -1L
    }

    fun loginUser(
        phone: String,
        password: String
    ): User? {

        val db = dbHelper.readableDatabase

        val hashedPassword = HashUtils.hashPassword(password)

        val cursor = db.rawQuery(
            """
            SELECT * FROM Users
            WHERE phone = ? AND password = ?
            """.trimIndent(),
            arrayOf(phone, hashedPassword)
        )

        var user: User? = null

        if (cursor.moveToFirst()) {

            user = User(
                uid = cursor.getString(
                    cursor.getColumnIndexOrThrow("uid")
                ),

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
                ),

                role = cursor.getString(
                    cursor.getColumnIndexOrThrow("role")
                ),

                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow("phone")
                ),

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow("password")
                ),

                roomNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow("room_number")
                )
            )
        }

        cursor.close()

        return user
    }

    private fun isPhoneExists(phone: String): Boolean {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT uid FROM Users
        WHERE phone = ?
        """.trimIndent(),
            arrayOf(phone)
        )

        val exists = cursor.count > 0

        cursor.close()

        return exists
    }

    private fun getUserCount(): Int {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM Users",
            null
        )

        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()

        return count
    }
    fun getUserById(userId: String): User? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT * FROM Users
        WHERE uid = ?
        """.trimIndent(),
            arrayOf(userId)
        )

        var user: User? = null

        if (cursor.moveToFirst()) {

            user = User(

                uid = cursor.getString(
                    cursor.getColumnIndexOrThrow("uid")
                ),

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
                ),

                role = cursor.getString(
                    cursor.getColumnIndexOrThrow("role")
                ),

                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow("phone")
                ),

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow("password")
                ),

                roomNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow("room_number")
                )
            )
        }

        cursor.close()

        return user
    }
    fun updateProfile(
        uid: String,
        name: String,
        phone: String,
        roomNumber: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {

            put("name", name)
            put("phone", phone)
            put("room_number", roomNumber)
        }

        val result = db.update(
            "Users",
            values,
            "uid = ?",
            arrayOf(uid)
        )

        return result > 0
    }

}