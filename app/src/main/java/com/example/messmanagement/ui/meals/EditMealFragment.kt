package com.example.messmanagement.ui.meals

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import android.widget.Toast
import com.example.messmanagement.data.db.DatabaseHelper
import com.example.messmanagement.data.model.Meal
import com.example.messmanagement.data.repository.MealRepository

class EditMealFragment : Fragment(R.layout.fragment_edit_meal) {
    private lateinit var mealRepository: MealRepository
    private var mealId: Int = -1

    private lateinit var editDate: EditText
    private lateinit var editLunchMenu: EditText
    private lateinit var editDinnerMenu: EditText
    private lateinit var btnUpdateMeal: Button
    private lateinit var btnDeleteMeal: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editDate = view.findViewById(R.id.editDate)
        editLunchMenu = view.findViewById(R.id.editLunchMenu)
        editDinnerMenu = view.findViewById(R.id.editDinnerMenu)
        btnUpdateMeal = view.findViewById(R.id.btnUpdateMeal)
        btnDeleteMeal = view.findViewById(R.id.btnDeleteMeal)
        mealRepository = MealRepository(requireContext())

        mealId = arguments?.getInt("meal_id", -1) ?: -1

        val date = arguments?.getString("date") ?: ""
        val lunchMenu = arguments?.getString("lunch_menu") ?: ""
        val dinnerMenu = arguments?.getString("dinner_menu") ?: ""

        editDate.setText(date)
        editLunchMenu.setText(lunchMenu)
        editDinnerMenu.setText(dinnerMenu)
        btnUpdateMeal.setOnClickListener {
            val updatedMeal = Meal(
                mealId = mealId,
                date = editDate.text.toString().trim(),
                lunchMenu = editLunchMenu.text.toString().trim(),
                dinnerMenu = editDinnerMenu.text.toString().trim()
            )

            val success = mealRepository.updateMeal(updatedMeal)

            if (success) {
                Toast.makeText(requireContext(), "Meal Updated Successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Failed to Update Meal", Toast.LENGTH_SHORT).show()
            }
        }

        btnDeleteMeal.setOnClickListener {
            val success = mealRepository.deleteMeal(mealId)

            if (success) {
                Toast.makeText(requireContext(), "Meal Deleted Successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Failed to Delete Meal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}