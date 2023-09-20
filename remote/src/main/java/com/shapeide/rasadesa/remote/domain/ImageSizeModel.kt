package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class ImageSizeModel(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)