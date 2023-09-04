package com.shapeide.rasadesa.domain

import com.shapeide.rasadesa.utills.smartTruncate

data class Ingredient(
    val idIngredient: Int,
    val strIngredient: String,
    val strDescription: String,
    val strType: String
){
    val shortDescription: String get() = strDescription.smartTruncate(50)
}