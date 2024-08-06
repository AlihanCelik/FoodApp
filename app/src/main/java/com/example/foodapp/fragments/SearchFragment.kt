package com.example.foodapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activites.MainActivity
import com.example.foodapp.adapter.FavoritesMealsAdapter
import com.example.foodapp.adapter.MealByCategoryAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.viewModel.HomeViewModel
import retrofit2.http.Query

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: FavoritesMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        searchAdapter=FavoritesMealsAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareSearchRecyclerView()
        observeSearchLiveData()
        setupTextWatcher()
    }
    private fun searchMeals(searcQuery:String){
        viewModel.searchMeals(searcQuery)

    }

    private fun observeSearchLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList->
            searchAdapter.differ.submitList(mealsList)

        })
    }

    private fun prepareSearchRecyclerView() {
        binding.searchRcw.apply {
            adapter=searchAdapter
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        }
    }
    private fun setupTextWatcher() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchMeals(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

}