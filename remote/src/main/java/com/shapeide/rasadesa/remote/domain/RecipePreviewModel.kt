package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class RecipePreviewModel(
    @SerializedName("uri") var uri: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("images") var images: ImagesModel? = ImagesModel(),
    @SerializedName("calories") var calories: Double? = null,
    @SerializedName("totalTime") var totalTime: Int? = null,
    @SerializedName("dietLabels") var dietLabels: ArrayList<String> = arrayListOf(),
){
    val parsedId = uri?.let { it.split("#")[1] } ?: ""
}