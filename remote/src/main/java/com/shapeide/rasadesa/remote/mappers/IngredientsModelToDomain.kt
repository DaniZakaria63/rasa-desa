package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.Ingredients
import com.shapeide.rasadesa.remote.domain.IngredientsModel


fun ArrayList<IngredientsModel>.toDomainModel(): List<Ingredients> {
    return map {
        Ingredients(
            it.text,
            it.quantity?.toInt(),
            it.measure,
            it.food,
            it.weight?.toInt(),
            it.foodCategory,
            it.foodId,
            it.image
        )
    }
}