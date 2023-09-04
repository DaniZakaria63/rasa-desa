package com.shapeide.rasadesa.remote.data.repository

import com.shapeide.rasadesa.remote.data.source.APIEndpoint
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import com.shapeide.rasadesa.remote.domain.HitsPreviewResponse
import com.shapeide.rasadesa.remote.domain.HitsSingleResponse
import com.shapeide.rasadesa.remote.domain.RecipeModel
import com.shapeide.rasadesa.remote.domain.RecipePreviewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implemented Source Based Network Request
 * by Retrofit
 * */
class DefaultNetworkRequest(private val apiEndpoint: APIEndpoint): NetworkRequest {
    override suspend fun getRecipes(
        mealType: String,
        isRandom: Boolean
    ): Flow<List<RecipePreviewModel>> = flow {
        val data:HitsPreviewResponse = apiEndpoint.getRecipes(
            mealType = mealType,
            isRandom = isRandom
        )
        emit(data.hits?: listOf())
    }

    override suspend fun getSingleRecipe(recipeId: String): Flow<RecipeModel> = flow {
        val data: HitsSingleResponse = apiEndpoint.getSingleRecipe(recipeId)
        emit(data.singleHit?:RecipeModel())
    }
}