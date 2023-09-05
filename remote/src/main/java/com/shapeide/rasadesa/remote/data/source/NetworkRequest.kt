package com.shapeide.rasadesa.remote.data.source

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.remote.domain.RecipeModel
import kotlinx.coroutines.flow.Flow

/**
 * Source Based Network Request
 * by Retrofit
 * */
interface NetworkRequest {
    suspend fun getRecipes(mealType: String, isRandom: Boolean = true) : Flow<List<RecipePreview>>
    suspend fun getSingleRecipe(recipeId: String): Flow<RecipeModel>
}