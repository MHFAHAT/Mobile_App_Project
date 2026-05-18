package com.example.messmanagement.ui.payments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.model.Payment

class PaymentAdapter(
    private val paymentList: List<Payment>
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    class PaymentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val textUserId: TextView =
            itemView.findViewById(R.id.textUserId)

        val textMonthYear: TextView =
            itemView.findViewById(R.id.textMonthYear)

        val textMealCount: TextView =
            itemView.findViewById(R.id.textMealCount)

        val textMealRate: TextView =
            itemView.findViewById(R.id.textMealRate)

        val textTotalAmount: TextView =
            itemView.findViewById(R.id.textTotalAmount)

        val textStatus: TextView =
            itemView.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_payment,
                parent,
                false
            )

        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PaymentViewHolder,
        position: Int
    ) {
        val payment = paymentList[position]

        holder.textUserId.text =
            "User ID: ${payment.userId}"

        holder.textMonthYear.text =
            payment.monthYear

        holder.textMealCount.text =
            "Meal Count: ${payment.mealCount}"

        holder.textMealRate.text =
            "Meal Rate: ${payment.mealRate}"

        holder.textTotalAmount.text =
            "Total Amount: ${payment.totalAmount}"

        holder.textStatus.text =
            "Status: ${payment.status}"
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }
}