package com.example.messmanagement.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.Notice

class NoticeRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllNotices(): List<Notice> {

        val noticeList = mutableListOf<Notice>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT * FROM Notices
            ORDER BY timestamp DESC
            """.trimIndent(),
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val notice = Notice(

                    noticeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("notice_id")
                    ),

                    title = cursor.getString(
                        cursor.getColumnIndexOrThrow("title")
                    ),

                    message = cursor.getString(
                        cursor.getColumnIndexOrThrow("message")
                    ),

                    timestamp = cursor.getString(
                        cursor.getColumnIndexOrThrow("timestamp")
                    )
                )

                noticeList.add(notice)

            } while (cursor.moveToNext())
        }

        cursor.close()

        return noticeList
    }

    fun addNotice(
        title: String,
        message: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {

            put("title", title)
            put("message", message)
        }

        val result = db.insert(
            "Notices",
            null,
            values
        )

        return result != -1L
    }

    fun deleteNotice(
        noticeId: Int
    ): Boolean {

        val db = dbHelper.writableDatabase

        val result = db.delete(
            "Notices",
            "notice_id = ?",
            arrayOf(noticeId.toString())
        )

        return result > 0
    }

    fun updateNotice(
        noticeId: Int,
        title: String,
        message: String
    ): Boolean {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {

            put("title", title)
            put("message", message)
        }

        val result = db.update(
            "Notices",
            values,
            "notice_id = ?",
            arrayOf(noticeId.toString())
        )

        return result > 0
    }
}