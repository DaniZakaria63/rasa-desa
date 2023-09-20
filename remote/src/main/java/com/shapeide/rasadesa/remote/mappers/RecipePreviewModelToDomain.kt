package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.remote.domain.RecipePreviewModel

fun List<RecipePreviewModel>.asDomainModel(): List<RecipePreview> {
    return map {
        RecipePreview(
            it.parsedId,
            it.uri,
            it.label,
            it.images?.THUMBNAIL?.url,
            it.mealType.listToString(),
            it.calories,
            it.totalTime,
            it.isFavorite,
            it.dietLabels
        )
    }
}