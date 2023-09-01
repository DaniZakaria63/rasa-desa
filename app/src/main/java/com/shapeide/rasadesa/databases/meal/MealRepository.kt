package com.shapeide.rasadesa.databases.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.domains.Meal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {

    val _favoriteData: LiveData<List<Meal>> = desaDatabase.mealDao.allFavorite().map {
        it.asDomainListModel()
    }.asLiveData()

    suspend fun getRandomMeal(): Meal =
        withContext(dispatcherProvider.io) {
            try {
                val randomMeal = apiEndpoint.getRandomMeal()
                setOneMealLocal(randomMeal.asDatabaseModel())
                randomMeal.asDomainModel()
            } catch (e: Exception) {
                getRandomMealLocal()
            }
        }

    private suspend fun getRandomMealLocal(): Meal =
        withContext(dispatcherProvider.io) {
            val data: MealEntity = desaDatabase.mealDao.findRandomOne()
            data.asDomainModel()
        }

    suspend fun getMealByID(id: Int): Meal? = withContext(dispatcherProvider.io) {
        try {
            val mealData: ResponseMeals<MealModel>? = apiEndpoint.getDetailMeal(id)
            if (mealData?.meals == null || mealData.meals.isEmpty()) return@withContext null
            setOneMealLocal(mealData.asDatabaseModel())
            return@withContext mealData.asDomainModel()
        } catch (e: Exception) {
            return@withContext getOneMealLocal(id)
        }

    }

    private fun getOneMealLocal(id: Int): Meal? { // already inside io dispatcher
        val mealData: MealEntity? = desaDatabase.mealDao.findOne(id)
        return mealData?.asDomainModel()
    }

    private fun setOneMealLocal(meal: MealEntity?) { // already inside io dispatcher
        if (meal != null) desaDatabase.mealDao.insertOneMeal(meal)
    }

    suspend fun deleteAllMeal() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.mealDao.deleteAll()
            }
        }
    }
}