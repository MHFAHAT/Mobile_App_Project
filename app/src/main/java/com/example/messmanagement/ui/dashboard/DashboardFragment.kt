package com.example.messmanagement.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.DashboardRepository
import com.example.messmanagement.session.SessionManager

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var sessionManager: SessionManager
    private lateinit var repository: DashboardRepository

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager =
            SessionManager(requireContext())

        repository =
            DashboardRepository(requireContext())

        val tvWelcome =
            view.findViewById<TextView>(R.id.tvWelcome)

        val tvRole =
            view.findViewById<TextView>(R.id.tvRole)

        val tvTotalMeals =
            view.findViewById<TextView>(R.id.tvTotalMeals)

        val tvTotalExpenses =
            view.findViewById<TextView>(R.id.tvTotalExpenses)

        val tvTotalNotices =
            view.findViewById<TextView>(R.id.tvTotalNotices)

        val tvPendingPayments =
            view.findViewById<TextView>(R.id.tvPendingPayments)

        val userName =
            sessionManager.getUserName()

        val role =
            sessionManager.getUserRole()

        tvWelcome.text =
            "Welcome, $userName"

        tvRole.text =
            "Role: $role"

        tvTotalMeals.text =
            "Total Meals: ${repository.getTotalMeals()}"

        tvTotalExpenses.text =
            "Total Expenses: ${repository.getTotalExpenses()}"

        tvTotalNotices.text =
            "Total Notices: ${repository.getTotalNotices()}"

        tvPendingPayments.text =
            "Pending Payments: ${repository.getPendingPayments()}"
    }
}