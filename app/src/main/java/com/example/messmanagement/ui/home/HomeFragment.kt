package com.example.messmanagement.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.ui.dashboard.DashboardFragment
import com.example.messmanagement.ui.expenses.ExpensesFragment
import com.example.messmanagement.ui.meals.MealsFragment
import com.example.messmanagement.ui.notices.NoticesFragment
import com.example.messmanagement.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.messmanagement.ui.payments.PaymentsFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        val bottomNavigation = view.findViewById<BottomNavigationView>(
            R.id.bottomNavigation
        )

        if (savedInstanceState == null) {

            childFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    DashboardFragment()
                )
                .commit()
        }

        bottomNavigation.setOnItemSelectedListener { item ->

            val fragment = when (item.itemId) {

                R.id.nav_dashboard -> DashboardFragment()

                R.id.nav_meals -> MealsFragment()

                R.id.nav_expenses -> ExpensesFragment()

                R.id.nav_payments -> PaymentsFragment()

                R.id.nav_notices -> NoticesFragment()

                R.id.nav_profile -> ProfileFragment()

                else -> DashboardFragment()
            }

            childFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    fragment
                )
                .commit()

            true
        }

        return view
    }
}