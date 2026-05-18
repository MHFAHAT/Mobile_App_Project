package com.example.messmanagement.ui.expenses

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.ExpenseRepository
import com.example.messmanagement.session.SessionManager

class ExpensesFragment : Fragment(R.layout.fragment_expenses) {

    private lateinit var recyclerExpenses: RecyclerView
    private lateinit var btnAddExpense: Button
    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerExpenses =
            view.findViewById(R.id.recyclerExpenses)

        btnAddExpense =
            view.findViewById(R.id.btnAddExpense)

        expenseRepository =
            ExpenseRepository(requireContext())

        sessionManager =
            SessionManager(requireContext())

        recyclerExpenses.layoutManager =
            LinearLayoutManager(requireContext())

        val role = sessionManager.getUserRole()

        if (role == "Resident") {
            btnAddExpense.visibility = View.GONE
        }

        btnAddExpense.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    AddExpenseFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    private fun loadExpenses() {

        val expenseList =
            expenseRepository.getAllExpenses()

        val adapter =
            ExpenseAdapter(expenseList)

        recyclerExpenses.adapter = adapter
    }
}