package com.example.easyrecipe.retrofit

import com.example.easyrecipe.pojo.CategoryList
import com.example.easyrecipe.pojo.MealsByCategoriesList
import com.example.easyrecipe.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoriesList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("search.php")
    suspend fun searchMeal(@Query("s") query: String): MealList

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoriesList>
}
