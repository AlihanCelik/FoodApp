package com.example.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.activites.MainActivity
import com.example.foodapp.adapter.CategoryAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter=CategoryAdapter()
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        perpareCategoryRecyclerView()
        observeCategoryLiveData()
    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoryAdapter.setCategories(categories)
        })
    }

    private fun perpareCategoryRecyclerView() {
        binding.categoryRcw.apply {
            adapter=categoryAdapter
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }


}