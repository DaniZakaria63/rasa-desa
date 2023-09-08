package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.remote.domain.RecipePreviewModel
import com.shapeide.rasadesa.remote.domain.RecipeSealedResponse

fun List<RecipeSealedResponse>.toRecipePreviewModelList(): List<RecipePreviewModel>{
    return map {
        it.recipe?:RecipePreviewModel()
    }
}