package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel():ViewModel() {
    private var mealDetailsLiveData=MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getRandomMeal(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    mealDetailsLiveData.value=randomMeal
                }else{
                    true
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }

        })
    }
    fun observeMealDetailLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
}