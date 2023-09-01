package com.shapeide.rasadesa.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.databases.search.MealSearchEntity
import com.shapeide.rasadesa.databases.search.asSearchDomain
import com.shapeide.rasadesa.domains.Search
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRoomManager @Inject constructor(
    val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {
    val _searchHistoryData = desaDatabase.searchDao.findAll().map { it.asSearchDomain() }.asLiveData()

    suspend fun queryMealSearch(query: String = ""): List<Search> = withContext(dispatcherProvider.io){
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

    private fun List<MealModel>.toSearchModel(): List<Search> {
        return map {
            Search(
                id = it.idMeal.toString(),
                text = it.strMeal.toString()
            )
        }
    }
}