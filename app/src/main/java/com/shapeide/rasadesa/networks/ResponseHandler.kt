package com.shapeide.rasadesa.networks

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseCategory<T>(val categories: List<T>)

@JsonClass(generateAdapter = true)
data class ResponseMeals<T>(val meals: List<T>)
