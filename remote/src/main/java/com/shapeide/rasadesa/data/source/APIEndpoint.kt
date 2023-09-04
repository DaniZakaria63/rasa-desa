package com.shapeide.rasadesa.data.source

import com.shapeide.rasadesa.domain.AreaModel
import com.shapeide.rasadesa.domain.CategoryModel
import com.shapeide.rasadesa.domain.FilterMealModel
import com.shapeide.rasadesa.domain.IngredientsModel
import com.shapeide.rasadesa.domain.MealModel
import com.shapeide.rasadesa.domain.ResponseCategory
import com.shapeide.rasadesa.domain.ResponseMeals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface APIEndpoint {
    companion object {
        @JvmStatic
        val BASE_URL = "http://www.themealdb.com"
    }

    // GET MEAL CATEGORIES
    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories(): ResponseCategory<CategoryModel>

    // GET LIST OF MEALS BY CATEGORIES
    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): ResponseMeals<FilterMealModel>

    // GET LIST OF AREA/COUNTRY
    @GET("/api/json/v1/1/list.php")
    suspend fun getAreas(@Query("a") area: String): ResponseMeals<AreaModel>

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