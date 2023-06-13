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
import javax.inject.Inject

/**
 * this is main repository for handle categories data
 */


class CategoryRepository @Inject constructor(private val roomDB: RoomDB, private val apiEndpoint: APIEndpoint) {
    val _categoryData: LiveData<List<Category>> =
        Transformations.map(roomDB.categoryDao.findAll()) {
            Log.d(TAG, "RoomRepo: transformation map")
            it.asDomainModel()
        }

    suspend fun syncCategory() {
        withContext(Dispatchers.IO) {
            // Getting from API
            val newData: ResponseCategory<CategoryModel> = apiEndpoint.getCategories()
            Log.d(TAG, "syncCategory: Already get an API of category data")

            //insert to local database
            roomDB.categoryDao.insertAll(newData.asDatabaseModel())
            Log.d(TAG, "syncCategory: was saving all data to local database")
        }
    }

    suspend fun getLocalCategory(): LiveData<List<Category>> {
        return withContext(Dispatchers.IO){
            Log.d(TAG, "getLocalCategory: Currently just getting data from database [Repository]")
            return@withContext Transformations.map(roomDB.categoryDao.findAll()) {
                it.asDomainModel()
            }
        }
    }

    suspend fun deleteLocalCategory() {
        Log.d(TAG, "deleteLocalCategory: Start to deleting all record from database")
        withContext(Dispatchers.IO) {
            roomDB.categoryDao.deleteAll()
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addOne(categoryEntity: CategoryEntity) {
        roomDB.categoryDao.addOne(categoryEntity)
    }
}