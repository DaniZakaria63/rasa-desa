package com.shapeide.rasadesa.local

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domain.Category
import com.shapeide.rasadesa.local.dao.CategoryDAO
import com.shapeide.rasadesa.local.entity.CategoryEntity
import com.shapeide.rasadesa.local.entity.asDomainModel
import com.shapeide.rasadesa.networks.*
import com.shapeide.rasadesa.networks.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext

/**
 * this is main repository for handle categories data
 */
class RoomRepository(private val database: RoomDB, private val network: APIEndpoint) {
    val _categoryData: LiveData<List<Category>> =
        Transformations.map(database.categoryDao.findAll()) {
            Log.d(TAG, "RoomRepo: transformation map")
            it.asDomainModel()
        }

    // check if there's a new data from internet or local one
    suspend fun syncCategory() {
        withContext(Dispatchers.IO) {
            // Getting from API
            val newData: ResponseCategory<CategoryModel> = network.getCategories()
            Log.d(TAG, "syncCategory: Already get an API of category data")
            Log.d(TAG, "syncCategory: ONE DATA: ${newData.categories.get(0)}")

            //insert to local database
            database.categoryDao.insertAll(newData.asDatabaseModel())
            Log.d(TAG, "syncCategory: was saving all data to local database")
        }
    }

    fun getLocalCategory(): LiveData<List<CategoryEntity>> {
        return database.categoryDao.findAll()
    }

    fun deleteLocalCategory() {
        database.categoryDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addOne(categoryEntity: CategoryEntity) {
        database.categoryDao.addOne(categoryEntity)
    }
}