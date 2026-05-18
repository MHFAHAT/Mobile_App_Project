package com.example.messmanagement.ui.expenses

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.ExpenseRepository
import com.example.messmanagement.session.SessionManager

class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val editDate =
            view.findViewById<EditText>(R.id.editDate)

        val editCategory =
            view.findViewById<EditText>(R.id.editCategory)

        val editAmount =
            view.findViewById<EditText>(R.id.editAmount)

        val btnSaveExpense =
            view.findViewById<Button>(R.id.btnSaveExpense)

        val expenseRepository =
            ExpenseRepository(requireContext())

        val sessionManager =
            SessionManager(requireContext())

        btnSaveExpense.setOnClickListener {

            val date = editDate.text.toString().trim()
            val category = editCategory.text.toString().trim()
            val amountText = editAmount.text.toString().trim()

            if (date.isEmpty() ||
                category.isEmpty() ||
                amountText.isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()

            if (amount == null) {
                Toast.makeText(
                    requireContext(),
                    "Invalid amount",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val userId = sessionManager.getUserId()

            if (userId.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "User session not found",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val success = expenseRepository.insertExpense(
                date = date,
                category = category,
                amount = amount,
                enteredBy = userId
            )

            if (success) {
                Toast.makeText(
                    requireContext(),
                    "Expense saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to save expense",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}