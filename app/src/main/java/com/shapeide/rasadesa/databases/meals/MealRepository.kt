package com.shapeide.rasadesa.databases.meals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.asDatabaseModel
import com.shapeide.rasadesa.networks.models.FilterMealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(private val roomDB: RoomDB, private val apiEndpoint: APIEndpoint) {
    val _filterMealData : LiveData<List<FilterMeal>> = Transformations.map(roomDB.mealDao.findAll_FM()){
        it.asDomainModel()
    }

    suspend fun syncByMeal(mealName: String){
        withContext(Dispatchers.IO){
            val newData: ResponseMeals<FilterMealModel> = apiEndpoint.getMealsByCategory(mealName)
            Log.d(TAG, "syncByMeal: Success to get data from internet")

            roomDB.mealDao.insertAll_FM(newData.asDatabaseModel())
            Log.d(TAG, "syncByMeal: Success to insert all data to local storage")
        }
    }
}