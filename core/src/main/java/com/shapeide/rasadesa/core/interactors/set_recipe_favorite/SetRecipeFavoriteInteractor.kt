package com.shapeide.rasadesa.core.interactors.set_recipe_favorite

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import javax.inject.Inject

class SetRecipeFavoriteInteractor @Inject constructor(
    val repository: RecipeRepository
){
    suspend operator fun invoke(recipeId: String, isFavorite: Boolean): Boolean{
        return repository.setRecipeFavorite(recipeId, isFavorite)
    }
}