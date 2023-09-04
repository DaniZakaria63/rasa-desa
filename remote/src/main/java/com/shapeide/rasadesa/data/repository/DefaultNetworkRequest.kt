package com.shapeide.rasadesa.data.repository

import com.shapeide.rasadesa.data.source.APIEndpoint
import com.shapeide.rasadesa.data.source.NetworkRequest
import com.shapeide.rasadesa.domain.AreaModel
import com.shapeide.rasadesa.domain.CategoryModel
import com.shapeide.rasadesa.domain.FilterMealModel
import com.shapeide.rasadesa.domain.IngredientsModel
import com.shapeide.rasadesa.domain.MealModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

/**
 * Implemented Source Based Network Request
 * by Retrofit
 * */
class DefaultNetworkRequest(private val apiEndpoint: APIEndpoint): NetworkRequest {
}