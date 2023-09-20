package com.shapeide.rasadesa.core.interactors.get_recipe_favorite_by_id

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import javax.inject.Inject

class GetRecipeFavoriteByIdInteractor @Inject constructor(
    val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: String): Boolean {
        return repository.getRecipeFavoriteById(recipeId)
    }
}