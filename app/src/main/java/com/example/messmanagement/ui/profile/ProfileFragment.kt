package com.example.messmanagement.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.messmanagement.MainActivity
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.UserRepository
import com.example.messmanagement.session.SessionManager

class ProfileFragment : Fragment() {

    private lateinit var repository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )

        repository = UserRepository(requireContext())
        sessionManager = SessionManager(requireContext())

        val tvUserId = view.findViewById<TextView>(R.id.tvUserId)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
        val tvRole = view.findViewById<TextView>(R.id.tvRole)
        val tvRoom = view.findViewById<TextView>(R.id.tvRoom)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)

        val userId = sessionManager.getUserId()

        if (userId != null) {
            val user = repository.getUserById(userId)
            if (user != null) {
                tvUserId.text = "User ID: ${user.uid}"
                tvName.text = "Name: ${user.name}"
                tvPhone.text = "Phone: ${user.phone}"
                tvRole.text = "Role: ${user.role}"
                tvRoom.text = "Room: ${user.roomNumber}"

                btnEditProfile.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("name", user.name)
                        putString("phone", user.phone)
                        putString("room_number", user.roomNumber)
                    }

                    val fragment = EditProfileFragment()
                    fragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.home_fragment_container,
                            fragment
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        btnLogout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(
                requireContext(),
                MainActivity::class.java
            )
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    } // <-- This is the end of onCreateView()

    // Step 3: Added onResume() below onCreateView, but inside the class
    override fun onResume() {
        super.onResume()

        val userId = sessionManager.getUserId()

        if (userId != null) {

            val user = repository.getUserById(userId)

            view?.findViewById<TextView>(R.id.tvUserId)
                ?.text = "User ID: ${user?.uid}"

            view?.findViewById<TextView>(R.id.tvName)
                ?.text = "Name: ${user?.name}"

            view?.findViewById<TextView>(R.id.tvPhone)
                ?.text = "Phone: ${user?.phone}"

            view?.findViewById<TextView>(R.id.tvRole)
                ?.text = "Role: ${user?.role}"

            view?.findViewById<TextView>(R.id.tvRoom)
                ?.text = "Room: ${user?.roomNumber}"
        }
    }
} // <-- This is the final closing brace of the class