package com.example.messmanagement.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.MealRepository
import com.example.messmanagement.session.SessionManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.messmanagement.data.repository.MealRequestRepository
class MealsFragment : Fragment() {

    private lateinit var repository: MealRepository

    // Step 1: Global class variables added here
    private lateinit var recyclerMeals: RecyclerView
    private lateinit var adapter: MealAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_meals,
            container,
            false
        )

        repository = MealRepository(requireContext())
        sessionManager = SessionManager(requireContext())

        // Step 2: Replaced 'val recyclerMeals' with global variable assignment
        recyclerMeals = view.findViewById(
            R.id.recyclerMeals
        )

        val btnAddMeal = view.findViewById<Button>(
            R.id.btnAddMeal
        )

        btnAddMeal.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    AddMealFragment()
                )
                .addToBackStack(null)
                .commit()
        }
        val requestSection = view.findViewById<LinearLayout>(
            R.id.requestSection
        )
        val checkLunch = view.findViewById<CheckBox>(R.id.checkLunch)
        val checkDinner = view.findViewById<CheckBox>(R.id.checkDinner)
        val btnSaveRequest = view.findViewById<Button>(R.id.btnSaveRequest)

        val mealRequestRepository = MealRequestRepository(requireContext())
        val role = sessionManager.getUserRole()

        if (role == "Resident") {

            btnAddMeal.visibility = View.GONE

            val userId = sessionManager.getUserId()

            if (!userId.isNullOrEmpty()) {

                val today = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).format(Date())

                val mealRequest = mealRequestRepository.getMealRequest(
                    userId,
                    today
                )

                mealRequest?.let {
                    checkLunch.isChecked = it.first
                    checkDinner.isChecked = it.second
                    btnSaveRequest.text = "Update Request"
                }
            }
            btnSaveRequest.setOnClickListener {

                val userId = sessionManager.getUserId()

                if (userId.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "User session not found",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val today = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).format(Date())

                val isLunchChecked = checkLunch.isChecked
                val isDinnerChecked = checkDinner.isChecked

                val success = mealRequestRepository.saveMealRequest(
                    userId,
                    today,
                    isLunchChecked,
                    isDinnerChecked
                )

                if (success) {
                    Toast.makeText(
                        requireContext(),
                        "Meal request saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to save meal request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        } else {

            requestSection.visibility = View.GONE
        }

        // Step 3: Replaced the old manual adapter setup block with the function call
        loadMeals()

        return view
    }

    // Step 4: Added loadMeals() function below onCreateView
    private fun loadMeals() {
        val mealList = repository.getAllMeals()

        adapter = MealAdapter(mealList) { selectedMeal ->
            val editMealFragment = EditMealFragment().apply {
                arguments = Bundle().apply {
                    putInt("meal_id", selectedMeal.mealId)
                    putString("date", selectedMeal.date)
                    putString("lunch_menu", selectedMeal.lunchMenu)
                    putString("dinner_menu", selectedMeal.dinnerMenu)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, editMealFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerMeals.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerMeals.adapter = adapter
    }

    // Step 5: Added onResume() lifecycle function below loadMeals()
    override fun onResume() {
        super.onResume()
        loadMeals()
    }
}