package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.networks.models.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * based on https://medium.com/dsc-sastra-deemed-to-be-university/retrofit-with-viewmodel-in-kotlin-part-2-15f395e32424
 */

interface APIEndpoint {
    companion object {
         private const val BASE_URL = BuildConfig.BASE_URL
        fun create(): APIEndpoint {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIEndpoint::class.java)
        }
    }

    // GET MEAL CATEGORIES
    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories(): ResponseCategory<CategoryModel>

    // GET LIST OF MEALS BY CATEGORIES
    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): ResponseMeals<FilterMealModel>

    // GET LIST OF AREA/COUNTRY
    @GET("/api/json/v1/1/list.php")
    suspend fun getArea(@Query("a") area: String): ResponseMeals<AreaModel>

    // GET LIST OF INGREDIENTS
    @GET("/api/json/v1/1/list.php")
    suspend fun getIngredients(@Query("i") ingredients: String): ResponseMeals<IngredientsModel>

    // GET LIST OF CATEGORY, JUST NAME OF THE CATEGORIES
    @GET("/api/json/v1/1/list.php")
    suspend fun getCategories(@Query("c") categories: String): ResponseMeals<CategoryModel>

    // GET LIST BY FILTER CATEGORIES, AREA, OR INGREDIENTS
    @GET("/api/json/v1/1/filter.php")
    suspend fun getDataWithFilter(@QueryMap mapString: Map<String?, String?>): ResponseMeals<FilterMealModel>

    // GET RANDOM MEAL
    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal(): ResponseMeals<MealModel>

    // GET ONE MEAL
    @GET("/api/json/v1/1/lookup.php")
    suspend fun getDetailMeal(@Query("i") id: Int) : ResponseMeals<MealModel>

    // GET MEAL FROM KEYWORD
    @GET("/api/json/v1/1/search.php")
    suspend fun getSearchMeal(@Query("s") keyword: String = "") : ResponseMeals<MealModel>

    // GET METHOD FOR ALL REQUEST, ESPECIALLY AT GET METHOD ONLY
    @GET("{url}")
    suspend fun <T> getRequest(
        @Path(value = "url", encoded = true) path: String,
        @QueryMap hashMap: HashMap<String, String>
    ): Response<T>
}