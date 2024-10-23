package com.vaibhavranga.expensetracker.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vaibhavranga.expensetracker.R
import com.vaibhavranga.expensetracker.databinding.ActivityMainBinding
import com.vaibhavranga.expensetracker.model.CategoryModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        val arrCat = ArrayList<CategoryModel>().apply {
            add(CategoryModel(0, "Transport", R.drawable.transport))
            add(CategoryModel(1, "Clothes", R.drawable.clothes))
            add(CategoryModel(2, "Food", R.drawable.fastfood))
            add(CategoryModel(3, "Fuel", R.drawable.fuel))
            add(CategoryModel(4, "Groceries", R.drawable.groceries))
            add(CategoryModel(5, "House rent", R.drawable.house_rent))
            add(CategoryModel(6, "Massage", R.drawable.massage))
            add(CategoryModel(7, "Movie", R.drawable.movie))
            add(CategoryModel(8, "Salary", R.drawable.salary))
            add(CategoryModel(9, "Salon", R.drawable.salon))
        }

        const val KEY_BALANCE = "balance"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fragmentManager = supportFragmentManager
        loadFragment(DashboardFragment())
        binding.bottomNavView.selectedItemId = R.id.bottom_nav_dashboard

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_dashboard -> loadFragment(DashboardFragment())
                else -> loadFragment(ChartsFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.frameLayout, fragment)
        ft.commit()
    }
}