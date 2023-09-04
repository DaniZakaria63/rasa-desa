package com.shapeide.rasadesa.domain

data class IngredientsModel(
    val idIngredient: Int,
    val strIngredient: String,
    val strDescription: String? = "",
    val strType: String? = "default"
)
