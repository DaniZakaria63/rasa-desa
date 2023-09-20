package com.shapeide.rasadesa.core.interactors.get_recipes

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import javax.inject.Inject

class GetRecipesInteractor @Inject constructor(
    val repository: RecipeRepository
) {
    suspend operator fun invoke(){
        repository.getRecipes()
    }
}