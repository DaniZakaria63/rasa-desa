package com.shapeide.rasadesa.domain.domain

data class RecipePreview(
    var id: String? = "",
    var uri: String? = "",
    var label: String? = "",
    var image: String? = "",
    var mealType: String? = "",
    var calories: Double? = 0.0,
    var totalTime: Int? = null,
    var isFavorite: Boolean? = false,
    var dietLabels: List<String> = arrayListOf(),
){
    val caloriesInt: Int? get() = calories?.toInt()
    val labelShorter: String get() = if((label?.length ?: 0) > 35) label?.take(35)+"..." else label.toString()
}