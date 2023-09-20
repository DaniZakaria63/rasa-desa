package com.shapeide.rasadesa.core.interactors.set_caching_recipes

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.domain.domain.RecipePreview
import javax.inject.Inject

class SetCachingRecipesListInteractor @Inject constructor(
    val repository: RecipeRepository
) {
    suspend operator fun invoke(recipes: List<RecipePreview>){
        repository.setCachingRecipeList(recipes)
    }
}