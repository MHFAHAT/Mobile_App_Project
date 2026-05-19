package com.example.messmanagement.ui.payments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.PaymentRepository
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.ui.payments.AddPaymentFragment
import android.widget.Toast

class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    private lateinit var recyclerPayments: RecyclerView
    private lateinit var btnAddPayment: Button
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerPayments =
            view.findViewById(R.id.recyclerPayments)

        btnAddPayment =
            view.findViewById(R.id.btnAddPayment)

        paymentRepository =
            PaymentRepository(requireContext())

        sessionManager =
            SessionManager(requireContext())

        recyclerPayments.layoutManager =
            LinearLayoutManager(requireContext())

        val role = sessionManager.getUserRole()

        if (role == "Resident") {
            btnAddPayment.visibility = View.GONE
        }

        btnAddPayment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    AddPaymentFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        loadPayments()
    }

    override fun onResume() {
        super.onResume()
        loadPayments()
    }

    private fun loadPayments() {

        val role = sessionManager.getUserRole()

        val paymentList = if (role == "Resident") {

            val userId =
                sessionManager.getUserId() ?: ""

            paymentRepository.getPaymentsByUser(userId)

        } else {
            paymentRepository.getAllPayments()
        }

        recyclerPayments.adapter =
            PaymentAdapter(paymentList) { payment ->

                val role =
                    sessionManager.getUserRole()

                if (role == "Resident") {
                    return@PaymentAdapter
                }

                val newStatus =
                    if (payment.status == "Paid") {
                        "Unpaid"
                    } else {
                        "Paid"
                    }

                val success =
                    paymentRepository.updatePaymentStatus(
                        payment.paymentId,
                        newStatus
                    )

                if (success) {

                    Toast.makeText(
                        requireContext(),
                        "Payment marked as $newStatus",
                        Toast.LENGTH_SHORT
                    ).show()

                    loadPayments()
                }
            }
    }
}