package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.remote.domain.RecipeModel
import com.shapeide.rasadesa.remote.domain.RecipeSealedResponse
import com.shapeide.rasadesa.remote.domain.RecipeSingleResponse

fun RecipeSingleResponse.toDomainModel() : Recipe =
    Recipe()