package com.shapeide.rasadesa.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.data.source.MealDataSource
import com.shapeide.rasadesa.databases.DesaDatabase
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
) : MealDataSource {

    val _favoriteData: LiveData<List<com.shapeide.rasadesa.domain.Meal>> = desaDatabase.mealDao.allFavorite().map {
        it.asDomainListModel()
    }.asLiveData()

    override suspend fun getRandomMeal(): com.shapeide.rasadesa.domain.Meal =
        withContext(dispatcherProvider.io) {
            try {
                val randomMeal = apiEndpoint.getRandomMeal()
                setOneMealLocal(randomMeal.asDatabaseModel())
                randomMeal.asDomainModel()
            } catch (e: Exception) {
                getRandomMealLocal()
            }
        }

    override suspend fun getRandomMealLocal(): com.shapeide.rasadesa.domain.Meal =
        withContext(dispatcherProvider.io) {
            val data: MealEntity = desaDatabase.mealDao.findRandomOne()
            data.asDomainModel()
        }

    override suspend fun getMealByID(id: Int): com.shapeide.rasadesa.domain.Meal? = withContext(dispatcherProvider.io) {
        try {
            val mealData: ResponseMeals<MealModel>? = apiEndpoint.getDetailMeal(id)
            if (mealData?.meals == null || mealData.meals.isEmpty()) return@withContext null
            setOneMealLocal(mealData.asDatabaseModel())
            return@withContext mealData.asDomainModel()
        } catch (e: Exception) {
            return@withContext getOneMealLocal(id)
        }

    }

    override fun getOneMealLocal(id: Int): com.shapeide.rasadesa.domain.Meal? { // already inside io dispatcher
        val mealData: MealEntity? = desaDatabase.mealDao.findOne(id)
        return mealData?.asDomainModel()
    }

    override fun setOneMealLocal(meal: MealEntity?) { // already inside io dispatcher
        if (meal != null) desaDatabase.mealDao.insertOneMeal(meal)
    }

    override fun deleteAllMeal() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                desaDatabase.mealDao.deleteAll()
            }
        }
    }
}