package com.shapeide.rasadesa.core.data.source

import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.domain.domain.RecipePreview
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipesByMealType(mealType: MealType): Flow<Result<List<RecipePreview>>>
    suspend fun getRecipes(): Flow<Result<List<RecipePreview>>>
    suspend fun getRecipeById(recipeId: String): Flow<Result<Recipe>>
    suspend fun getRecipeFavorite(): Flow<Result<List<RecipePreview>>>
    suspend fun setRecipeFavorite(recipeId: String, isFavorite: Boolean): Boolean
    suspend fun getRecipeFavoriteById(recipeId: String): Boolean
    suspend fun setCachingRecipeList(recipeList: List<RecipePreview>)
}