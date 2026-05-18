package com.example.messmanagement.ui.payments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.PaymentRepository

class AddPaymentFragment : Fragment(R.layout.fragment_add_payment) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val editUserId =
            view.findViewById<EditText>(R.id.editUserId)

        val editMonthYear =
            view.findViewById<EditText>(R.id.editMonthYear)

        val editMealCount =
            view.findViewById<EditText>(R.id.editMealCount)

        val editMealRate =
            view.findViewById<EditText>(R.id.editMealRate)

        val editTotalAmount =
            view.findViewById<EditText>(R.id.editTotalAmount)

        val btnSavePayment =
            view.findViewById<Button>(R.id.btnSavePayment)

        val paymentRepository =
            PaymentRepository(requireContext())

        btnSavePayment.setOnClickListener {

            val userId =
                editUserId.text.toString().trim()

            val monthYear =
                editMonthYear.text.toString().trim()

            val mealCountText =
                editMealCount.text.toString().trim()

            val mealRateText =
                editMealRate.text.toString().trim()

            val totalAmountText =
                editTotalAmount.text.toString().trim()

            if (userId.isEmpty() ||
                monthYear.isEmpty() ||
                mealCountText.isEmpty() ||
                mealRateText.isEmpty() ||
                totalAmountText.isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val mealCount =
                mealCountText.toIntOrNull()

            val mealRate =
                mealRateText.toDoubleOrNull()

            val totalAmount =
                totalAmountText.toDoubleOrNull()

            if (mealCount == null ||
                mealRate == null ||
                totalAmount == null
            ) {
                Toast.makeText(
                    requireContext(),
                    "Invalid numeric values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val success =
                paymentRepository.insertPayment(
                    userId = userId,
                    monthYear = monthYear,
                    mealCount = mealCount,
                    mealRate = mealRate,
                    totalAmount = totalAmount
                )

            if (success) {
                Toast.makeText(
                    requireContext(),
                    "Payment saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to save payment",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}