package com.shapeide.rasadesa.core.interactors.get_recipes_with_params

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.RecipePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesWithParamsInteractor @Inject constructor(
    val repository: RecipeRepository
) {
    suspend operator fun invoke(mealType: MealType): Flow<Result<List<RecipePreview>>> {
        return repository.getRecipesByMealType(mealType)
    }
}