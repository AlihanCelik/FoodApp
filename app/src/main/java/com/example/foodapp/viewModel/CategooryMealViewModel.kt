package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class CategooryMealViewModel():ViewModel() {
    val mealsLiveData=MutableLiveData<List<MealByCategory>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let { categoryMeal ->
                    mealsLiveData.postValue(categoryMeal.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }
    fun observeMealsByCategoryLiveData(): MutableLiveData<List<MealByCategory>> {
        return mealsLiveData
    }

}
