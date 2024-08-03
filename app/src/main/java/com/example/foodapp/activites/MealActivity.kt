package com.example.foodapp.activites

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealYotubeUrl:String
    private lateinit var mealMVVM:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mealMVVM=ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observeMealDetailLiveData()
        onYoutubeImageClick()

    }
    private fun onYoutubeImageClick(){
        binding.youtubeBtn.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(mealYotubeUrl))
            startActivity(intent)
        }
    }

    private fun observeMealDetailLiveData() {
        mealMVVM.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                binding.category.text="Category : ${value!!.strCategory}"
                binding.area.text="Area : ${value.strArea}"
                binding.txt2.text="Category : ${value.strInstructions}"
                mealYotubeUrl=value.strYoutube
                onRepsonseCase()
            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setInformationInViews(){
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent(){
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!


    }
    private  fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnAddToFav.visibility=View.INVISIBLE
        binding.txt2.visibility=View.INVISIBLE
        binding.category.visibility=View.INVISIBLE
        binding.area.visibility=View.INVISIBLE
        binding.youtubeBtn.visibility=View.INVISIBLE
    }
    private  fun onRepsonseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnAddToFav.visibility=View.VISIBLE
        binding.txt2.visibility=View.VISIBLE
        binding.category.visibility=View.VISIBLE
        binding.area.visibility=View.VISIBLE
        binding.youtubeBtn.visibility=View.VISIBLE
    }
}