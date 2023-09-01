package com.shapeide.rasadesa.databases.category

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseCategory
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import com.shapeide.rasadesa.utills.isOnline
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
) {
    val _categoryData: LiveData<List<Category>> = desaDatabase.categoryDao.findAll().map {
        it.asDomainModel()
    }.asLiveData()

    suspend fun syncCategory() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                val newData: ResponseCategory<CategoryModel> = apiEndpoint.getCategories()
                insertCategory(newData.asDatabaseModel())
            }
        }
    }

    private fun insertCategory(datas: List<CategoryEntity>) {
        desaDatabase.categoryDao.insertAll(datas)
    }

    suspend fun deleteLocalCategory() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.categoryDao.deleteAll()
            }
        }
    }
}