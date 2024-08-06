package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase):ViewModel() {
    private var mealDetailsLiveData=MutableLiveData<Meal>()
    private var favoritesLiveData=MutableLiveData<List<Meal>>()

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
    fun observeFavoriteLiveData(): LiveData<List<Meal>> {
        return favoritesLiveData
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }

}