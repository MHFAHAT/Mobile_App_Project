package com.example.messmanagement.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.UserRepository
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.ui.dashboard.DashboardFragment

class LoginFragment : Fragment() {

    private lateinit var repository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_login,
            container,
            false
        )

        repository = UserRepository(requireContext())
        sessionManager = SessionManager(requireContext())

        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        val tvRegister = view.findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {

            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val user = repository.loginUser(
                phone,
                password
            )

            if (user != null) {

                sessionManager.saveLoginSession(
                    user.uid,
                    user.name,
                    user.role
                )

                Toast.makeText(
                    requireContext(),
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        DashboardFragment()
                    )
                    .commit()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Invalid phone or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tvRegister.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    RegisterFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}