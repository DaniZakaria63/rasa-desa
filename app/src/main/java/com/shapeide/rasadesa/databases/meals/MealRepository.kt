package com.shapeide.rasadesa.databases.meals

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.RasaApplication
import com.shapeide.rasadesa.utills.isOnline
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    /* Make the data depends by category name */
    private val _filterMealData = MutableLiveData<List<FilterMeal>>()
    val filterMealData: LiveData<List<FilterMeal>> get() = _filterMealData

    /*
    val _filterMealData : LiveData<List<FilterMeal>> = Transformations.map(roomDB.mealDao.findAll_FM()){
        it.asDomainModel()
    }
    */

    suspend fun syncByMeal(mealName: String) {
        if (isOnline(context)) {
            val updatedMealsData = getFilterMealsInternet(mealName)
            _filterMealData.postValue(updatedMealsData.asDomainModel())
            setFilterMealsLocal(updatedMealsData.asDatabaseModel())
            Log.d(TAG, "syncByMeal: Sync to new data")
        } else {
            val updatedMealsData = getFilterMealsLocal(mealName)
            _filterMealData.postValue(updatedMealsData)
            Log.d(TAG, "syncByMeal: Sync to local data")
        }
    }

    /* Getting Meal From Internet, By Category Name */
    suspend fun getFilterMealsInternet(mealName: String): ResponseMeals<FilterMealModel> {
        val newData: ResponseMeals<FilterMealModel> = apiEndpoint.getMealsByCategory(mealName)
        newData.meals.map { data -> data.typeMeal = mealName }
        Log.d(TAG, "syncByMeal: Success get filter data from internet")
        return newData
    }

    /* Save the Meal to Local, all of the data */
    suspend fun setFilterMealsLocal(datas: List<FilterMealEntity>) = withContext(Dispatchers.IO) {
        roomDB.mealDao.insertAll_FM(datas)
        Log.d(TAG, "syncByMeal: Success to insert filter meals to local")
    }

    /* Query from local based on/where category data */
    suspend fun getFilterMealsLocal(mealName: String): List<FilterMeal> =
        withContext(Dispatchers.IO) {
            val datas: List<FilterMealEntity> = roomDB.mealDao.findByName_FM(mealName)
            Log.d(TAG, "getFilterMealsLocal: Done get the local filter meals data")
            return@withContext datas.entityAsDomainModel()
        }
}