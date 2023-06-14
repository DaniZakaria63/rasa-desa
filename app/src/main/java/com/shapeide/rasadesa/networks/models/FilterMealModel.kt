package com.shapeide.rasadesa.networks.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterMealModel(
    val idMeal: Int,
    var typeMeal: String = "Beef",
    val strMeal: String,
    val strMealThumb: String
)
