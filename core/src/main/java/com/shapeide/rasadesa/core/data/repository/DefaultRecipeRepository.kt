package com.shapeide.rasadesa.core.data.repository

import com.shapeide.rasadesa.domain.coroutines.DispatcherProvider
import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import com.shapeide.rasadesa.room.data.source.RecipeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    val recipeDataStore: RecipeDataStore,
    val networkRequest: NetworkRequest,
    val dispatcherProvider: DispatcherProvider
) : RecipeRepository {

    /*TODO: Implement this function*/
    override suspend fun getRecipes(): Flow<Result<List<com.shapeide.rasadesa.domain.domain.RecipePreview>>> = flow{
        emit(Result.success(listOf()))
    }

    /*Check whether online api is not working*/
    override suspend fun getRecipesByMealType(mealType: com.shapeide.rasadesa.domain.domain.MealType): Flow<Result<List<com.shapeide.rasadesa.domain.domain.RecipePreview>>> {
        return networkRequest.getRecipes(mealType.name)
            .catch {
                merge(recipeDataStore.getRecipesByType(mealType.name)).catch {
                    Result.failure<List<com.shapeide.rasadesa.domain.domain.RecipePreview>>(it)
                }
            }
            .map { value: List<com.shapeide.rasadesa.domain.domain.RecipePreview> ->
                Result.success(value)
            }
            .flowOn(dispatcherProvider.io)
    }
}

