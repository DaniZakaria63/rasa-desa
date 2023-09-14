package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.remote.domain.RecipeModel

fun RecipeModel.toDomainModel(): Recipe =
    Recipe(
        uri,
        label,
        image,
        source,
        url,
        shareAs,
        yield,
        dietLabels,
        healthLabels,
        cautions,
        ingredientLines,
        ingredients.toDomainModel(),
        calories, totalWeight, totalTime,
        totalNutrients?.toDomainModel(),
        totalDaily?.toDomainModel(),
        digest.toDomainModel()
    )
