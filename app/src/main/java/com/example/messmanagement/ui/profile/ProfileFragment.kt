package com.example.messmanagement.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        val userId = sessionManager.getUserId()

        if (userId != null) {

            val user = repository.getUserById(userId)

            if (user != null) {

                tvUserId.text = "User ID: ${user.uid}"
                tvName.text = "Name: ${user.name}"
                tvPhone.text = "Phone: ${user.phone}"
                tvRole.text = "Role: ${user.role}"
                tvRoom.text = "Room: ${user.roomNumber}"
            }
        }

        return view
    }
}