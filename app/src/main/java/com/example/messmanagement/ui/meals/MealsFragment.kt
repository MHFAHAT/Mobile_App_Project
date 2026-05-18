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
        val role = sessionManager.getUserRole()

        if (role == "Resident") {

            btnAddMeal.visibility = View.GONE
        }

        // Step 3: Replaced the old manual adapter setup block with the function call
        loadMeals()

        return view
    }

    // Step 4: Added loadMeals() function below onCreateView
    private fun loadMeals() {
        val mealList = repository.getAllMeals()

        adapter = MealAdapter(mealList)

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