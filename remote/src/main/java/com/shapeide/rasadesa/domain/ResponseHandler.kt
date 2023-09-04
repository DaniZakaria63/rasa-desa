package com.shapeide.rasadesa.domain

data class ResponseCategory<T>(val categories: List<T>)

data class ResponseMeals<T>(val meals: List<T>)
