package com.shapeide.rasadesa.networks.models

import com.squareup.moshi.JsonClass

/* Response handler from API */

@JsonClass(generateAdapter = true)
data class CategoryModel(
    val idCategory: Int,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)
