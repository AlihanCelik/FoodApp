package com.example.foodapp.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapter.MealByCategoryAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewModel.CategooryMealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var CategoryMealsMVVM:CategooryMealViewModel
    private lateinit var categoryMealAdapter:MealByCategoryAdapter
    private lateinit var CATEGORY_NAME:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealAdapter= MealByCategoryAdapter()
        prepareRecyclerView()
        CategoryMealsMVVM= ViewModelProviders.of(this)[CategooryMealViewModel::class.java]
        CATEGORY_NAME=intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        CategoryMealsMVVM.getMealsByCategory(CATEGORY_NAME)
        binding.textViewCategory.text=CATEGORY_NAME
        CategoryMealsMVVM.observeMealsByCategoryLiveData().observe(this, Observer { mealsList->
            mealsList.forEach{
                categoryMealAdapter.setMealByCategory(mealsList)
            }
        })


    }

    private fun prepareRecyclerView() {
        binding.mealsRcw.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }
    }
}