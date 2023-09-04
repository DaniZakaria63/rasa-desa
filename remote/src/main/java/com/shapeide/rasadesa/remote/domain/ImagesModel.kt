package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class ImagesModel(
    @SerializedName("THUMBNAIL") var THUMBNAIL: ImageSizeModel? = ImageSizeModel(),
    @SerializedName("SMALL") var SMALL: ImageSizeModel? = ImageSizeModel(),
    @SerializedName("REGULAR") var REGULAR: ImageSizeModel? = ImageSizeModel()
)
