package com.shapeide.rasadesa.domain.domain

data class Recipe (
    var uri: String? = null,
    var label: String? = null,
    var image: String? = null,
    var source: String? = null,
    var url: String? = null,
    var shareAs: String? = null,
    var yield: Int? = null,
    var dietLabels: List<String> = listOf(),
    var healthLabels: List<String> = listOf(),
    var cautions: List<String> = listOf(),
    var ingredientLines: List<String> = listOf(),
    var ingredients: List<Ingredients> = listOf(),
    var calories: Double? = null,
    var totalWeight: Double? = null,
    var totalTime: Int? = null,
    var totalNutrients: Nutrients? = Nutrients(),
    var totalDaily: NutrientsDaily? = NutrientsDaily(),
    var digest: List<Digest> = listOf()
)