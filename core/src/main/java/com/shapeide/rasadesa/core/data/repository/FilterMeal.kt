package com.shapeide.rasadesa.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shapeide.rasadesa.core.data.source.FilterMealDataSource
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterMeal @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) : com.shapeide.rasadesa.core.data.source.FilterMealDataSource {
    /* Make the data depends by category name */
    private val _filterMealData = MutableLiveData<List<com.shapeide.rasadesa.core.domain.FilterMeal>>()
    val filterMealData: LiveData<List<com.shapeide.rasadesa.core.domain.FilterMeal>> get() = _filterMealData


    override suspend fun syncByMeal(mealName: String) {
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
    override suspend fun getFilterMealsInternet(mealName: String): ResponseMeals<FilterMealModel> {
        val newData: ResponseMeals<FilterMealModel> = apiEndpoint.getMealsByCategory(mealName)
        newData.meals.map { data -> data.typeMeal = mealName }
        return newData
    }


    /* Save the Meal to Local, all of the data */
    override fun setFilterMealsLocal(datas: List<FilterMealEntity>) {
        desaDatabase.filterMealDao.insertAll_FM(datas)
    }


    /* Query from local based on/where category data */
    override fun getFilterMealsLocal(mealName: String): List<com.shapeide.rasadesa.core.domain.FilterMeal> {
        val datas: List<FilterMealEntity> = desaDatabase.filterMealDao.findByName_FM(mealName)
        return datas.entityAsDomainModel()
    }
}