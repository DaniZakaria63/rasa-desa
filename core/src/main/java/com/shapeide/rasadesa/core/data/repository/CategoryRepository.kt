package com.shapeide.rasadesa.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.core.data.source.CategoryDataSource
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.core.domain.Category
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseCategory
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * this is main repository for handle categories data
 */


class CategoryRepository @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) : com.shapeide.rasadesa.core.data.source.CategoryDataSource {
    val _categoryData: LiveData<List<com.shapeide.rasadesa.core.domain.Category>> = desaDatabase.categoryDao.findAll().map {
        it.asDomainModel()
    }.asLiveData()

    override suspend fun syncCategory() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                val newData: ResponseCategory<CategoryModel> = apiEndpoint.getCategories()
                insertCategory(newData.asDatabaseModel())
            }
        }
    }

    override fun insertCategory(datas: List<CategoryEntity>) {
        desaDatabase.categoryDao.insertAll(datas)
    }

    override suspend fun deleteLocalCategory() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.categoryDao.deleteAll()
            }
        }
    }
}