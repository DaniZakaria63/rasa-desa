package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.models.CategoryModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

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
    @GET("api/json/v1/1/categories.php")
    fun getCategories() : Call<List<CategoryModel>>
}