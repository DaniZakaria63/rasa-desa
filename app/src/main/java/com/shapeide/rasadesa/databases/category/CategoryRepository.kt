package com.shapeide.rasadesa.databases.category

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.networks.*
import com.shapeide.rasadesa.networks.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * this is main repository for handle categories data
 */
class CategoryRepository(private val database: RoomDB, private val network: APIEndpoint) {
    val _categoryData: LiveData<List<Category>> =
        Transformations.map(database.categoryDao.findAll()) {
            Log.d(TAG, "RoomRepo: transformation map")
            it.asDomainModel()
        }

    suspend fun syncCategory() {
        withContext(Dispatchers.IO) {
            // Getting from API
            val newData: ResponseCategory<CategoryModel> = network.getCategories()
            Log.d(TAG, "syncCategory: Already get an API of category data")

            //insert to local database
            database.categoryDao.insertAll(newData.asDatabaseModel())
            Log.d(TAG, "syncCategory: was saving all data to local database")
        }
    }

    suspend fun getLocalCategory(): LiveData<List<Category>> {
        return withContext(Dispatchers.IO){
            Log.d(TAG, "getLocalCategory: Currently just getting data from database [Repository]")
            return@withContext Transformations.map(database.categoryDao.findAll()) {
                it.asDomainModel()
            }
        }
    }

    suspend fun deleteLocalCategory() {
        Log.d(TAG, "deleteLocalCategory: Start to deleting all record from database")
        withContext(Dispatchers.IO) {
            database.categoryDao.deleteAll()
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addOne(categoryEntity: CategoryEntity) {
        database.categoryDao.addOne(categoryEntity)
    }
}