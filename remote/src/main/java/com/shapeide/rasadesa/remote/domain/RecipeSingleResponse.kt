package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class RecipeSingleResponse(
    @SerializedName("recipe") var recipe: RecipeModel? = null
)