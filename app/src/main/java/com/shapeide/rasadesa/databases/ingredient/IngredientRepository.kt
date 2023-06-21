package com.shapeide.rasadesa.databases.ingredient

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.Ingredient
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.IngredientsModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    val _ingredientData: LiveData<List<Ingredient>> =
        Transformations.map(roomDB.ingredientDao.findALl()) {
            it.asDomainModel()
        }

    suspend fun syncIngredient() {
        if (isOnline(context = context)) {
            val newData: ResponseMeals<IngredientsModel> = apiEndpoint.getIngredients("list")
            Log.d(TAG, "syncIngredient: IngredientRepo: refresh data from internet")
            insertAll(newData.asDatabaseModel())
        }
    }

    private suspend fun insertAll(datas: List<IngredientEntity>) {
        Log.d(TAG, "insertAll: IngredientRepo: insert all data into local")
        withContext(Dispatchers.IO) {
            roomDB.ingredientDao.insertAll(datas)
        }
    }

    suspend fun deleteAll() {
        Log.d(TAG, "deleteAll: DeleteRepo: delete all the local data")
        withContext(Dispatchers.IO) {
            roomDB.ingredientDao.deleteAll()
        }
    }
}