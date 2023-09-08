package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class HitsPreviewResponse(
    @SerializedName("hits") var hits: List<RecipeSealedResponse>? = null,
)

data class RecipeSealedResponse(
    @SerializedName("recipe") var recipe: RecipePreviewModel? = null
)