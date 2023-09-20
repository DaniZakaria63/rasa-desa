package com.shapeide.rasadesa.remote.data.source

import com.shapeide.rasadesa.remote.domain.HitsPreviewResponse
import com.shapeide.rasadesa.remote.domain.RecipeSealedResponse
import com.shapeide.rasadesa.remote.domain.RecipeSingleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIEndpoint {
    companion object {
        @JvmStatic
        val BASE_URL = "https://api.edamam.com"
    }

    // Recipe List With Query
    @GET("/api/recipes/v2")
    suspend fun getRecipes(
        @Query("type") type: String = "any",
        @Query("mealType") mealType: String = "Breakfast",
        @Query("random") isRandom: Boolean = true
    ): HitsPreviewResponse

    // Recipe Detail
    @GET("/api/recipes/v2/{id}")
    suspend fun getSingleRecipe(
        @Path("id") recipeId: String = "null",
        @Query("type") type: String = "public",
    ): RecipeSingleResponse
}