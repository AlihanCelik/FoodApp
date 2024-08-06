package com.example.foodapp.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapter.MealByCategoryAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_ID
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
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
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        onMealItemClick()


    }

    private fun onMealItemClick() {
        categoryMealAdapter.onItemClick={meal->
            val intent=Intent(this,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }

    }

    private fun prepareRecyclerView() {
        binding.mealsRcw.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }
    }
}