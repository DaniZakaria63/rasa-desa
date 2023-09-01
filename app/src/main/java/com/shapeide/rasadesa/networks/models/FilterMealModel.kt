package com.shapeide.rasadesa.networks.models

data class FilterMealModel(
    val idMeal: Int,
    var typeMeal: String = "Beef",
    val strMeal: String,
    val strMealThumb: String
)
