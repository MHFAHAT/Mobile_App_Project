package com.example.messmanagement.session

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(
        "mess_session",
        Context.MODE_PRIVATE
    )

    companion object {

        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_ROLE = "user_role"

    }

    fun saveLoginSession(
        userId: String,
        userName: String,
        role: String
    ) {

        prefs.edit()
            .putString(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, userName)
            .putString(KEY_USER_ROLE, role)
            .apply()
    }

    fun isLoggedIn(): Boolean {

        return prefs.contains(KEY_USER_ID)
    }

    fun getUserId(): String? {

        return prefs.getString(KEY_USER_ID, null)
    }

    fun getUserName(): String? {

        return prefs.getString(KEY_USER_NAME, null)
    }

    fun getUserRole(): String? {

        return prefs.getString(KEY_USER_ROLE, null)
    }

    fun logout() {

        prefs.edit().clear().apply()
    }
}