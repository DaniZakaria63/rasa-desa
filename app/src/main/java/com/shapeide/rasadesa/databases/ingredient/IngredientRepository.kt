package com.shapeide.rasadesa.databases.ingredient

import android.util.Log
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.IngredientsModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {
    val _ingredientData =
        desaDatabase.ingredientDao.findAll().map { it.asDomainModel() }.asLiveData()

    suspend fun syncIngredient() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                try {
                    val newData: ResponseMeals<IngredientsModel> =
                        apiEndpoint.getIngredients("list")
                    Log.d(TAG, "syncIngredient: IngredientRepo: refresh data from internet")
                    insertAll(newData.asDatabaseModel())
                } catch (e: Exception) {
                    Log.e(TAG, "syncIngredient: Error Network Exception", e)
                }
            }
        }
    }

    private suspend fun insertAll(datas: List<IngredientEntity>) {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.ingredientDao.insertAll(datas)
            }
        }
    }

    suspend fun deleteAll() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.ingredientDao.deleteAll()
            }
        }
    }
}