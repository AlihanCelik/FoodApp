package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel() {
    private var randomMealLveData=MutableLiveData<Meal>()
    private var popularMealLveData=MutableLiveData<List<MealByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
    private var favoritesMealLiveData=mealDatabase.mealDao().getAllMeals()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    randomMealLveData.value=randomMeal
                }else{
                    true
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if(response.body()!=null){
                    popularMealLveData.value=response.body()!!.meals
                }else{
                    true
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLveData
    }
    fun observePopularMealLiveData(): LiveData<List<MealByCategory>> {
        return popularMealLveData
    }
    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }
    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>>{
        return favoritesMealLiveData
    }
    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }
}