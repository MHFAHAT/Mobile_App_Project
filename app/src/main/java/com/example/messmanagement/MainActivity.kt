package com.example.messmanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.ui.auth.LoginFragment
import com.example.messmanagement.ui.dashboard.DashboardFragment

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        if (savedInstanceState == null) {

            val fragment = if (sessionManager.isLoggedIn()) {
                DashboardFragment()
            } else {
                LoginFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}