package com.example.messmanagement.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.MealRepository

class AddMealFragment : Fragment() {

    private lateinit var repository: MealRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_add_meal,
            container,
            false
        )

        repository = MealRepository(requireContext())

        val etDate = view.findViewById<EditText>(R.id.etDate)

        val etLunch = view.findViewById<EditText>(R.id.etLunch)

        val etDinner = view.findViewById<EditText>(R.id.etDinner)

        val btnSaveMeal = view.findViewById<Button>(
            R.id.btnSaveMeal
        )

        btnSaveMeal.setOnClickListener {

            val date = etDate.text.toString().trim()

            val lunch = etLunch.text.toString().trim()

            val dinner = etDinner.text.toString().trim()

            if (
                date.isEmpty() ||
                lunch.isEmpty() ||
                dinner.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val success = repository.addMeal(
                date,
                lunch,
                dinner
            )

            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Meal Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Meal for this date already exists",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}