package com.example.messmanagement.ui.expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.model.Expense

class ExpenseAdapter(
    private val expenseList: List<Expense>
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val textCategory: TextView =
            itemView.findViewById(R.id.textCategory)

        val textDate: TextView =
            itemView.findViewById(R.id.textDate)

        val textAmount: TextView =
            itemView.findViewById(R.id.textAmount)

        val textEnteredBy: TextView =
            itemView.findViewById(R.id.textEnteredBy)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_expense,
                parent,
                false
            )

        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
        position: Int
    ) {
        val expense = expenseList[position]

        holder.textCategory.text = expense.category
        holder.textDate.text = expense.date
        holder.textAmount.text = "Amount: ${expense.amount}"
        holder.textEnteredBy.text =
            "Entered By: ${expense.enteredBy ?: "N/A"}"
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }
}