package com.shapeide.rasadesa.core.data.repository

import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import com.shapeide.rasadesa.local.data.source.RecipeDataStore
import com.shapeide.rasadesa.remote.domain.RecipeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    val recipeDataStore: RecipeDataStore,
    val networkRequest: NetworkRequest,
    val dispatcherProvider: DispatcherProvider
) : RecipeRepository {

    /*TODO: Implement this function*/
    override suspend fun getRecipes(): Flow<Result<List<RecipePreview>>> = flow {
        emit(Result.success(listOf()))
    }

    /*Check whether online api is not working*/
    override suspend fun getRecipesByMealType(mealType: MealType): Flow<Result<List<RecipePreview>>> {
        return networkRequest.getRecipes(mealType.name)
//            .catch {
//                merge(recipeDataStore.getRecipesByType(mealType.name)).catch {
//                    Result.failure<List<RecipePreview>>(it)
//                }
//            }
            .map { value: List<RecipePreview> ->
                Result.success(value)
            }
            .catch { emit(Result.failure(it)) }
            .flowOn(dispatcherProvider.io)
    }

    override suspend fun getRecipeById(recipeId: String): Flow<Result<Recipe>> {
        return networkRequest.getSingleRecipe(recipeId)
            .map { value: Recipe ->
                Result.success(value)
            }.catch { emit(Result.failure(it)) }
            .flowOn(dispatcherProvider.io)
    }
}

