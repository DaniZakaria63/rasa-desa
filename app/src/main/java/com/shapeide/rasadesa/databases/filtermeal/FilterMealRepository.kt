package com.shapeide.rasadesa.databases.filtermeal

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterMealRepository @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {
    /* Make the data depends by category name */
    private val _filterMealData = MutableLiveData<List<FilterMeal>>()
    val filterMealData: LiveData<List<FilterMeal>> get() = _filterMealData


    suspend fun syncByMeal(mealName: String) {
        coroutineScope {
            launch(dispatcherProvider.io) {
                try {
                    val updatedMealsData = getFilterMealsInternet(mealName)
                    _filterMealData.postValue(updatedMealsData.asDomainModel())
                    setFilterMealsLocal(updatedMealsData.asDatabaseModel())
                } catch (e: Exception) {
                    val updatedMealsData = getFilterMealsLocal(mealName)
                    _filterMealData.postValue(updatedMealsData)
                }
            }
        }
    }


    /* Getting Meal From Internet, By Category Name */
    private suspend fun getFilterMealsInternet(mealName: String): ResponseMeals<FilterMealModel> {
        val newData: ResponseMeals<FilterMealModel> = apiEndpoint.getMealsByCategory(mealName)
        newData.meals.map { data -> data.typeMeal = mealName }
        return newData
    }


    /* Save the Meal to Local, all of the data */
    private fun setFilterMealsLocal(datas: List<FilterMealEntity>) {
        desaDatabase.filterMealDao.insertAll_FM(datas)
    }


    /* Query from local based on/where category data */
    private fun getFilterMealsLocal(mealName: String): List<FilterMeal> {
        val datas: List<FilterMealEntity> = desaDatabase.filterMealDao.findByName_FM(mealName)
        return datas.entityAsDomainModel()
    }
}