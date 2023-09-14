package com.shapeide.rasadesa.local.data.repository

import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.local.data.dao.RecipePreviewDao
import com.shapeide.rasadesa.local.data.source.RecipeDataStore
import com.shapeide.rasadesa.local.mappers.asDomainModel
import com.shapeide.rasadesa.local.mappers.toDatabaseModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    override suspend fun setAllRecipes(data: List<RecipePreview>) {
        coroutineScope {
            launch (dispatcherProvider.io){
                recipePreviewDao.insertAll(data.toDatabaseModel())
            }
        }
    }
}