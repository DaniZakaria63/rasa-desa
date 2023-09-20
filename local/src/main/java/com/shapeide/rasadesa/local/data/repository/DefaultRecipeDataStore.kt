package com.shapeide.rasadesa.local.data.repository

import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.local.data.dao.RecipePreviewDao
import com.shapeide.rasadesa.local.data.source.RecipeDataStore
import com.shapeide.rasadesa.local.mappers.asDomainModel
import com.shapeide.rasadesa.local.mappers.toDatabaseModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DefaultRecipeDataStore @Inject constructor(
    private val recipePreviewDao: RecipePreviewDao,
    private val dispatcherProvider: DispatcherProvider
) : RecipeDataStore {
    override suspend fun getAllRecipes(): Flow<List<RecipePreview>> =
        recipePreviewDao.findAll()
            .map { it.asDomainModel() }

    override suspend fun getRecipesByType(typeMeal: String): Flow<List<RecipePreview>> =
        recipePreviewDao.findByMealType(typeMeal)
            .map { it.asDomainModel() }

    override suspend fun getRecipesFavorite(): Flow<List<RecipePreview>> =
        recipePreviewDao.findAllByFavorite()
            .map { it.asDomainModel() }

    override suspend fun getRecipeFavoriteById(recipeId: String): Boolean =
        withContext(dispatcherProvider.io) {
            recipePreviewDao.collectFavorite(recipeId)
        }

    override suspend fun setRecipesFavoriteItem(recipeId: String, isFavorite: Boolean)
    = withContext(dispatcherProvider.io){
        val update = launch(dispatcherProvider.io){
            recipePreviewDao.updateFavorite(recipeId, isFavorite)
        }
        val current = async (dispatcherProvider.io){
            recipePreviewDao.collectFavorite(recipeId)
        }
        update.join()
        current.await()
    }

    override suspend fun setAllRecipes(data: List<RecipePreview>) {
        coroutineScope {
            launch (dispatcherProvider.io){
                recipePreviewDao.insertAll(data.toDatabaseModel())
            }
        }
    }
}