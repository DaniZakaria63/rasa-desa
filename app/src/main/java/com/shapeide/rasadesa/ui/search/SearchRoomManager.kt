package com.shapeide.rasadesa.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.search.MealSearchEntity
import com.shapeide.rasadesa.databases.search.asSearchDomain
import com.shapeide.rasadesa.domains.Search
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRoomManager @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    val _searchHistoryData: LiveData<List<Search>> =
        Transformations.map(roomDB.searchDao.findAll()) {
            it.asSearchDomain()
        }

    suspend fun queryMealSearch(query: String = ""): List<Search> {
        /* looking for the result online */
        if (isOnline(context)) {
            Log.d(BuildConfig.TAG, "queryMealSearch: getting data from API")
            val result: ResponseMeals<MealModel> = apiEndpoint.getSearchMeal(query)
            return result.meals.toSearchModel()
        } else {
            val result: List<MealSearchEntity> = getLocalSearch(query)
            return result.asSearchDomain()
        }
    }

    suspend fun getLocalSearch(query: String): List<MealSearchEntity> =
        withContext(Dispatchers.IO) {
            return@withContext roomDB.searchDao.querySearch(query)
        }

    suspend fun saveMealSearch(meal: MealSearchEntity) = withContext(Dispatchers.IO) {
        roomDB.searchDao.insertOneSearch(meal)
    }

    suspend fun deleteMealSearch(searchId: String) = withContext(Dispatchers.IO) {
        roomDB.searchDao.deleteOne(searchId)
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