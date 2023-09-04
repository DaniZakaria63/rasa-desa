package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class HitsPreviewResponse(
    @SerializedName("hits") var hits: List<RecipePreviewModel>? = null,
)
