package com.example.easyrecipe.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyrecipe.pojo.MealsByCategoriesList
import com.example.easyrecipe.pojo.MealsByCategory
import com.example.easyrecipe.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){

val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName: String){
         RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoriesList>{
             override fun onResponse(
                 call: Call<MealsByCategoriesList>,
                 response: Response<MealsByCategoriesList>
             ) {
                    response.body()?.let { mealsList->
                        mealsLiveData.postValue(mealsList.meals)
                    }
             }

             override fun onFailure(call: Call<MealsByCategoriesList>, t: Throwable) {
                 Log.e("CategoryMealsViewModel", t.message.toString())
             }

         })
    }

    fun observeMealsLiveData(): LiveData<List<MealsByCategory>>{
        return  mealsLiveData
    }
}