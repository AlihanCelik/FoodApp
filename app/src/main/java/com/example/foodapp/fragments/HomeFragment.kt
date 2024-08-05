package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapter.PopularFoodAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import kotlin.time.measureTime


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdpater:PopularFoodAdapter
    companion object{
        const val MEAL_ID="com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME="com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB="com.example.foodapp.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm= ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularAdpater=PopularFoodAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecylerView()

        homeMvvm.getRandomMeal()

        observerRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularItems()
        observePopularMealLiveData()

    }

    private fun preparePopularItemsRecylerView() {
        binding.rcw1.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularAdpater
        }
    }

    private fun observePopularMealLiveData() {
        homeMvvm.observePopularMealLiveData().observe(viewLifecycleOwner) { mealList ->
            popularAdpater.setMeals(mealList as ArrayList<CategoryMeals>)

        }
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
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)
            this@HomeFragment.randomMeal = meal


        }
    }


}