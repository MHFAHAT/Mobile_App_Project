package com.example.messmanagement.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.ui.auth.LoginFragment

class DashboardFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_dashboard,
            container,
            false
        )

        sessionManager = SessionManager(requireContext())

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val tvRole = view.findViewById<TextView>(R.id.tvRole)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val userName = sessionManager.getUserName()
        val role = sessionManager.getUserRole()

        tvWelcome.text = "Welcome, $userName"
        tvRole.text = "Role: $role"

        btnLogout.setOnClickListener {

            sessionManager.logout()

            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    LoginFragment()
                )
                .commit()
        }

        return view
    }
}