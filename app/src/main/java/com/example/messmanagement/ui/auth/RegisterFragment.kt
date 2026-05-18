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

class RegisterFragment : Fragment() {

    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_register,
            container,
            false
        )

        repository = UserRepository(requireContext())

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etRoom = view.findViewById<EditText>(R.id.etRoom)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)

        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        val tvLogin = view.findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {

            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val room = etRoom.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (
                name.isEmpty() ||
                phone.isEmpty() ||
                room.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val success = repository.registerUser(
                name,
                phone,
                room,
                password
            )

            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Registration Successful",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        LoginFragment()
                    )
                    .commit()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Phone number already exists",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tvLogin.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    LoginFragment()
                )
                .commit()
        }

        return view
    }
}