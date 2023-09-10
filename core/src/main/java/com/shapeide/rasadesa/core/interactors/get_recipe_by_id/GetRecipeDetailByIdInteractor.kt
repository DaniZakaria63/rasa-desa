package com.shapeide.rasadesa.core.interactors.get_recipe_by_id

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.domain.domain.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeDetailByIdInteractor @Inject constructor(
    val repository: RecipeRepository
){
    suspend operator fun invoke(recipeId: String): Flow<Result<Recipe>>{
        return repository.getRecipeById(recipeId)
    }
}