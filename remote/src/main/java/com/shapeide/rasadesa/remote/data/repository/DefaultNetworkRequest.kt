package com.shapeide.rasadesa.remote.data.repository

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.remote.data.source.APIEndpoint
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import com.shapeide.rasadesa.remote.domain.HitsPreviewResponse
import com.shapeide.rasadesa.remote.domain.HitsSingleResponse
import com.shapeide.rasadesa.remote.domain.RecipeModel
import com.shapeide.rasadesa.remote.mappers.asDomainModel
import com.shapeide.rasadesa.remote.mappers.toRecipePreviewModelList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implemented Source Based Network Request
 * by Retrofit
 * */
class DefaultNetworkRequest @Inject constructor(
    private val apiEndpoint: APIEndpoint
): NetworkRequest {
    override suspend fun getRecipes(
        mealType: String,
        isRandom: Boolean
    ): Flow<List<RecipePreview>> = flow {
        val data:HitsPreviewResponse = apiEndpoint.getRecipes(
            mealType = mealType,
            isRandom = isRandom
        )
        emit(data.hits?.toRecipePreviewModelList()?.asDomainModel()?: listOf())
    }

    override suspend fun getSingleRecipe(recipeId: String): Flow<RecipeModel> = flow {
        val data: HitsSingleResponse = apiEndpoint.getSingleRecipe(recipeId)
        emit(data.singleHit?:RecipeModel())
    }
}