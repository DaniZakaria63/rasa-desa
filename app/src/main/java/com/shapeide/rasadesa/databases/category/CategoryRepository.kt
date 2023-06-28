package com.shapeide.rasadesa.databases.category

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.networks.*
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * this is main repository for handle categories data
 */


class CategoryRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    val _categoryData: LiveData<List<Category>> = roomDB.categoryDao.findAll().map {
            it.asDomainModel()
        }.asLiveData()

    suspend fun syncCategory() {
        if (isOnline(context)) {
            // Getting from API
            val newData: ResponseCategory<CategoryModel> = apiEndpoint.getCategories()
            insertCategory(newData.asDatabaseModel())
            Log.d(TAG, "syncCategory: Already get an API of category data")
        }
    }

    suspend fun insertCategory(datas: List<CategoryEntity>) {
        roomDB.categoryDao.insertAll(datas)
        Log.d(TAG, "syncCategory: was saving all data to local database")
    }

    suspend fun deleteLocalCategory() {
        Log.d(TAG, "deleteLocalCategory: Start to deleting all record from database")
        roomDB.categoryDao.deleteAll()
    }
}