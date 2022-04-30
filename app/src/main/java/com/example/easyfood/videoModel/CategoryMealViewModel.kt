package com.example.easyfood.videoModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.model.MealsByCategoryList
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel(): ViewModel() {

    private var mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
               if(response.body() != null){
                   mealsLiveData.value = response.body()!!.meals
               }else{
                   response
               }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeLiveData(): LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}