package com.example.messmanagement.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.data.repository.UserRepository
import android.widget.Toast

class EditProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_edit_profile,
            container,
            false
        )

        sessionManager =
            SessionManager(requireContext())

        repository =
            UserRepository(requireContext())

        repository =
            UserRepository(requireContext())

        val etName =
            view.findViewById<EditText>(R.id.etName)

        val etPhone =
            view.findViewById<EditText>(R.id.etPhone)

        val etRoomNumber =
            view.findViewById<EditText>(R.id.etRoomNumber)

        val btnUpdateProfile =
            view.findViewById<Button>(R.id.btnUpdateProfile)

        val name =
            arguments?.getString("name") ?: ""

        val phone =
            arguments?.getString("phone") ?: ""

        val roomNumber =
            arguments?.getString("room_number") ?: ""

        val userId =
            sessionManager.getUserId() ?: ""

        etName.setText(name)
        etPhone.setText(phone)
        etRoomNumber.setText(roomNumber)

        btnUpdateProfile.setOnClickListener {

            val updatedName =
                etName.text.toString().trim()

            val updatedPhone =
                etPhone.text.toString().trim()

            val updatedRoom =
                etRoomNumber.text.toString().trim()

            if (
                updatedName.isEmpty() ||
                updatedPhone.isEmpty() ||
                updatedRoom.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val success =
                repository.updateProfile(
                    userId,
                    updatedName,
                    updatedPhone,
                    updatedRoom
                )

            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Update Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}