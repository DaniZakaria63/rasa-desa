package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("uri") var uri: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("images") var images: ImagesModel? = ImagesModel(),
    @SerializedName("source") var source: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("shareAs") var shareAs: String? = null,
    @SerializedName("yield") var yield: Int? = null,
    @SerializedName("dietLabels") var dietLabels: ArrayList<String> = arrayListOf(),
    @SerializedName("healthLabels") var healthLabels: ArrayList<String> = arrayListOf(),
    @SerializedName("cautions") var cautions: ArrayList<String> = arrayListOf(),
    @SerializedName("ingredientLines") var ingredientLines: ArrayList<String> = arrayListOf(),
    @SerializedName("ingredients") var ingredients: ArrayList<IngredientsModel> = arrayListOf(),
    @SerializedName("calories") var calories: Double? = null,
    @SerializedName("totalWeight") var totalWeight: Double? = null,
    @SerializedName("totalTime") var totalTime: Int? = null,
    @SerializedName("totalNutrients") var totalNutrients: TotalNutrientsModel? = TotalNutrientsModel(),
    @SerializedName("totalDaily") var totalDaily: TotalDailyModel? = TotalDailyModel(),
    @SerializedName("digest") var digest: ArrayList<DigestModel> = arrayListOf()
)