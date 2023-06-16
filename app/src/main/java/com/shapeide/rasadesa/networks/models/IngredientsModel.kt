package com.shapeide.rasadesa.networks.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientsModel(
    val idIngredient: Int,
    val strIngredient: String,
    val strDescription: String? = "",
    val strType: String? = "default"
)
