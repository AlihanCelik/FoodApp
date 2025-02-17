package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activites.CategoryMealsActivity
import com.example.foodapp.activites.MainActivity
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapter.CategoryAdapter
import com.example.foodapp.adapter.PopularFoodAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragments.bottomsheet.MealBottomSheetFragemnt
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import java.util.zip.ZipFile


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdpater:PopularFoodAdapter
    private lateinit var categoryAdpater: CategoryAdapter
    companion object{
        const val MEAL_ID="com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME="com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB="com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.foodapp.fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= (activity as MainActivity).viewModel
        popularAdpater=PopularFoodAdapter()
        categoryAdpater= CategoryAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecylerView()
        prepareCategoryRecylerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularMealLiveData()
        onPopularItemClick()

        viewModel.getCategories()
        observeCategoryLiveData()
        onCategoryItemClick()

        onPopularItemLongClick()

        onSearchItemClick()

    }

    private fun onSearchItemClick() {
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularAdpater.onLongItemClick={meal->
            val mealBottomSheetFragemnt=MealBottomSheetFragemnt.newInstance(meal.idMeal)
            mealBottomSheetFragemnt.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryItemClick() {
         categoryAdpater.onItemClick={category ->
             val intent=Intent(activity,CategoryMealsActivity::class.java)
             intent.putExtra(CATEGORY_NAME,category.strCategory)
             startActivity(intent)

         }
    }

    private fun prepareCategoryRecylerView() {
        binding.rcw2.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdpater
        }
    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner){ category->
            categoryAdpater.setCategories(category as ArrayList<Category>)
        }
    }


    private fun onPopularItemClick() {
        popularAdpater.onItemClick={meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecylerView() {
        binding.rcw1.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularAdpater
        }
    }

    private fun observePopularMealLiveData() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { mealList->
            popularAdpater.setMeals(mealList as ArrayList<MealByCategory>) }
    }

    private fun onRandomMealClick(){
        binding.cardView1.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)
            this@HomeFragment.randomMeal = meal


        }
    }


}