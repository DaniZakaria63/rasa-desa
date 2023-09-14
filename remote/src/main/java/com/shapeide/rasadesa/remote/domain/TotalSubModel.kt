package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class TotalSubModel(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null,
)