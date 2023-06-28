package com.shapeide.rasadesa.databases.meal

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.Meal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class MealRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {

    val _favoriteData: LiveData<List<Meal>> = roomDB.mealDao.allFavorite().map {
        it.asDomainListModel()
    }.asLiveData()

    suspend fun getRandomMeal(): Meal {
        if (!isOnline(context)) return getRandomMealLocal()

        val randomMeal = apiEndpoint.getRandomMeal()
        setOneMealLocal(randomMeal.asDatabaseModel())
        return randomMeal.asDomainModel()
    }

    suspend fun getRandomMealLocal(): Meal {
        val data: MealEntity = roomDB.mealDao.findRandomOne()
        return data.asDomainModel()
    }

    suspend fun getMealByID(id: Int): Meal? {
        if (!isOnline(context)) {
            val data: Meal? = getOneMealLocal(id)
            return data
        }

        val mealData: ResponseMeals<MealModel> = apiEndpoint.getDetailMeal(id)
        if (mealData.meals == null || mealData.meals.isEmpty()) return null
        setOneMealLocal(mealData.asDatabaseModel())
        return mealData.asDomainModel()
    }

    suspend fun getOneMealLocal(id: Int): Meal? {
        val mealData: MealEntity? = roomDB.mealDao.findOne(id)
        return mealData?.asDomainModel()
    }

    suspend fun setOneMealLocal(meal: MealEntity?) {
        if (meal != null) roomDB.mealDao.insertOneMeal(meal)
        Log.d(TAG, "setOneMealLocal: Done save the meal local")
    }

    suspend fun deleteAllMeal() {
        roomDB.mealDao.deleteAll()
    }
}