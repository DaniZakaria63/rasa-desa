package com.shapeide.rasadesa.local.mappers

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.local.domain.RecipePreviewEntity

fun List<RecipePreviewEntity>.asDomainModel(): List<RecipePreview> {
    return map {
        RecipePreview(
            it._id,
            it.uri,
            it.label,
            it.image,
            it.mealType,
            it.calories,
            it.totalTime,
            it.isFavorite,
            it.diets
        )
    }
}

fun RecipePreviewEntity.asDomainModel(): RecipePreview {
    return RecipePreview(_id, uri, label, image, mealType, calories, totalTime, isFavorite, diets)
}