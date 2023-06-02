package com.shapeide.rasadesa.databases.meals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.asDatabaseModel
import com.shapeide.rasadesa.networks.models.FilterMealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealRepository(private val dao: MealDAO, private val network: APIEndpoint) {
    val _filterMealData : LiveData<List<FilterMeal>> = Transformations.map(dao.findAll_FM()){
        it.asDomainModel()
    }

    suspend fun syncByMeal(mealName: String){
        withContext(Dispatchers.IO){
            val newData: ResponseMeals<FilterMealModel> = network.getMealsByCategory(mealName)
            Log.d(TAG, "syncByMeal: Success to get data from internet")

            dao.insertAll_FM(newData.asDatabaseModel())
            Log.d(TAG, "syncByMeal: Success to insert all data to local storage")
        }
    }
}