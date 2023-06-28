package com.shapeide.rasadesa.databases.ingredient

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.Ingredient
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.IngredientsModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    val _ingredientData  = roomDB.ingredientDao.findAll().map { it.asDomainModel() }.asLiveData()

    suspend fun syncIngredient() {
        if (isOnline(context = context)) {
            val newData: ResponseMeals<IngredientsModel> = apiEndpoint.getIngredients("list")
            Log.d(TAG, "syncIngredient: IngredientRepo: refresh data from internet")
            insertAll(newData.asDatabaseModel())
        }
    }

    private suspend fun insertAll(datas: List<IngredientEntity>) {
        Log.d(TAG, "insertAll: IngredientRepo: insert all data into local")
        roomDB.ingredientDao.insertAll(datas)
    }

    suspend fun deleteAll() {
        Log.d(TAG, "deleteAll: DeleteRepo: delete all the local data")
        roomDB.ingredientDao.deleteAll()
    }
}