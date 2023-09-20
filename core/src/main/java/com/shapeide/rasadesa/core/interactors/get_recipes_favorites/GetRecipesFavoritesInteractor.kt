package com.shapeide.rasadesa.core.interactors.get_recipes_favorites

import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.domain.domain.RecipePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesFavoritesInteractor @Inject constructor(
    val repository: RecipeRepository
) {
    suspend operator fun invoke(): Flow<Result<List<RecipePreview>>> {
        return repository.getRecipeFavorite()
    }
}