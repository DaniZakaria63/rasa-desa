package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIEndpoint {
    companion object{
        val BASE_URL = BuildConfig.BASE_URL
        fun create() : APIEndpoint{
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIEndpoint::class.java)
        }
    }

    // GET MEAL CATEGORIES
    @GET("/api/json/v1/1/categories.php")
    fun getCategories() : Call<ResponseCategory<CategoryModel>>

    // GET LIST OF MEALS BY CATEGORIES
    @GET("/api/json/v1/1/filter.php")
    fun getMealsByCategory(@Query("c") category: String) : Call<ResponseMeals<FilterMealModel>>

    // GET LIST OF AREA/COUNTRY
    @GET("/api/json/v1/1/list.php")
    fun getArea(@Query("a") area: String) : Call<ResponseMeals<AreaModel>>

    // GET LIST OF INGREDIENTS
    @GET("/api/json/v1/1/list.php")
    fun getIngredients(@Query("i") ingredients: String) : Call<ResponseMeals<IngredientsModel>>

    // GET LIST OF CATEGORY, JUST NAME OF THE CATEGORIES
    @GET("/api/json/v1/1/list.php")
    fun getCategories(@Query("c") categories: String) : Call<ResponseMeals<CategoryModel>>
}