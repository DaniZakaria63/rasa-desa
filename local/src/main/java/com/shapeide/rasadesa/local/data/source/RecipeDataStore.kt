package com.shapeide.rasadesa.local.data.source

import com.shapeide.rasadesa.domain.domain.RecipePreview
import kotlinx.coroutines.flow.Flow

interface RecipeDataStore {
    suspend fun getAllRecipes() : Flow<List<RecipePreview>>
    suspend fun getRecipesByType(typeMeal: String): Flow<List<RecipePreview>>
    suspend fun setAllRecipes(data: List<RecipePreview>)
    suspend fun getRecipesFavorite(): Flow<List<RecipePreview>>
    suspend fun setRecipesFavoriteItem(recipeId: String, isFavorite: Boolean): Boolean
    suspend fun getRecipeFavoriteById(recipeId: String): Boolean
}