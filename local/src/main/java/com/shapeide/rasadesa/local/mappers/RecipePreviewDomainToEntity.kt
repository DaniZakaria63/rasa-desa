package com.shapeide.rasadesa.local.mappers

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.local.domain.RecipePreviewEntity

fun List<RecipePreview>.toDatabaseModel(): List<RecipePreviewEntity> {
    return map {
        RecipePreviewEntity(
            0,
            it.id,
            it.uri,
            it.label,
            it.image,
            it.mealType,
            it.calories,
            it.totalTime,
            it.isFavorite,
            it.dietLabels
        )
    }
}