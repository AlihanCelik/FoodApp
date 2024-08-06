package com.example.foodapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activites.MainActivity
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.databinding.FragmentMealBottomSheetFragemntBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragemnt : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetFragemntBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMealBottomSheetFragemntBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetMealLiveData()
        onBottomSheetDialogClick()
    }

    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            if(mealName!=null && mealThumb!=null){
                val intent=Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheetMealLiveData() {
        viewModel.observeBottomSheetMeallLiveData().observe(viewLifecycleOwner, Observer { meal->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBtmImg)
            binding.mealName.text=meal.strMeal
            binding.category.text=meal.strCategory
            binding.area.text=meal.strArea

            mealName=meal.strMeal
            mealThumb=meal.strMealThumb
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragemnt().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}