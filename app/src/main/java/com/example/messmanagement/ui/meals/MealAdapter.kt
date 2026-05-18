package com.example.messmanagement.ui.meals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.model.Meal

class MealAdapter(
    private val mealList: List<Meal>
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView =
            itemView.findViewById(R.id.tvDate)

        val tvLunch: TextView =
            itemView.findViewById(R.id.tvLunch)

        val tvDinner: TextView =
            itemView.findViewById(R.id.tvDinner)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_meal,
                parent,
                false
            )

        return MealViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MealViewHolder,
        position: Int
    ) {

        val meal = mealList[position]

        holder.tvDate.text = meal.date

        holder.tvLunch.text =
            "Lunch: ${meal.lunchMenu}"

        holder.tvDinner.text =
            "Dinner: ${meal.dinnerMenu}"
    }

    override fun getItemCount(): Int {

        return mealList.size
    }
}