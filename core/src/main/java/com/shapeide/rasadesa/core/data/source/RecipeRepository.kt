package com.shapeide.rasadesa.core.data.source

import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.RecipePreview
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipesByMealType(mealType: MealType): Flow<Result<List<RecipePreview>>>
    suspend fun getRecipes(): Flow<Result<List<RecipePreview>>>
}