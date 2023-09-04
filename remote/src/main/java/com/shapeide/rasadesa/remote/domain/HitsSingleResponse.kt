package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class HitsSingleResponse(
    @SerializedName("hits") var hits: List<RecipeModel>? = null,
){
    val singleHit = hits?.let { it[0] }
}
