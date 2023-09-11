package com.shapeide.rasadesa.presenter.detail.state

import com.shapeide.rasadesa.domain.domain.Digest
import com.shapeide.rasadesa.domain.domain.Ingredients
import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.domain.domain.NutrientsDaily
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.RecipeDataState
import com.shapeide.rasadesa.presenter.domain.DetailTab

data class DetailScreenState(
    val header: Header = Header(),
    val others: Others = Others(),
    val ingredients: List<Ingredients>? = listOf<Ingredients>(),
    val nutrients: Nutrients? = Nutrients(),
    val dailyNutrients: NutrientsDaily? = NutrientsDaily(),
    val digest: List<Digest>? = listOf(),
    var moreRecipes: List<RecipePreview>? = listOf(),
) {
    data class Header(
        val calories: String = "",
        val time: String = "",
        val weight: String = "",
        val source: String = "",
        val sourceLink: String = "",
        val image: String =""
    )

    data class Others(
        val emission: String? = "",
        val emissionClass: String? = "",
        val cuisineType: List<String>? = listOf(),
        val mealType: List<String>? = listOf(),
        val healthLabels: List<String>? = listOf()
    )
}