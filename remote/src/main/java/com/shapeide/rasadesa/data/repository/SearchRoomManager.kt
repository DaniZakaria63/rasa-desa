package com.shapeide.rasadesa.data.repository

import android.util.Log
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.room.data.repository.DesaDatabase
import com.shapeide.rasadesa.room.domain.MealSearchEntity
import com.shapeide.rasadesa.room.domain.asSearchDomain
import com.shapeide.rasadesa.data.source.APIEndpoint
import com.shapeide.rasadesa.domain.ResponseMeals
import com.shapeide.rasadesa.domain.MealModel
import com.shapeide.rasadesa.coroutines.DispatcherProvider
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRoomManager @Inject constructor(
    val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {
    val _searchHistoryData = desaDatabase.searchDao.findAll().map { it.asSearchDomain() }.asLiveData()

    suspend fun queryMealSearch(query: String = ""): List<com.shapeide.rasadesa.domain.Search> = withContext(dispatcherProvider.io){
        try {
            Log.d(BuildConfig.TAG, "queryMealSearch: getting data from API")
            val result: ResponseMeals<MealModel> = apiEndpoint.getSearchMeal(query)
            return@withContext result.meals.toSearchModel()
        } catch (e: Exception) {
            val result: List<MealSearchEntity> = getLocalSearch(query)
            return@withContext result.asSearchDomain()
        }
    }

    suspend fun getLocalSearch(query: String): List<MealSearchEntity> {
        return desaDatabase.searchDao.querySearch(query)
    }

    suspend fun saveMealSearch(meal: MealSearchEntity) {
        desaDatabase.searchDao.insertOneSearch(meal)
    }

    suspend fun deleteMealSearch(searchId: String) {
        desaDatabase.searchDao.deleteOne(searchId)
    }

    private fun List<MealModel>.toSearchModel(): List<com.shapeide.rasadesa.domain.Search> {
        return map {
            com.shapeide.rasadesa.domain.Search(
                id = it.idMeal.toString(),
                text = it.strMeal.toString()
            )
        }
    }
}