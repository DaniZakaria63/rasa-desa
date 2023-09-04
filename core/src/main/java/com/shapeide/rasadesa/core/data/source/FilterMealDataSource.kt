package com.shapeide.rasadesa.core.data.source

import com.shapeide.rasadesa.core.domain.FilterMeal

interface FilterMealDataSource {
    suspend fun syncByMeal(mealName: String)

    /* Getting Meal From Internet, By Category Name */
    suspend fun getFilterMealsInternet(mealName: String): Any

    /* Save the Meal to Local, all of the data */
    fun setFilterMealsLocal(datas: List<Any>)

    /* Query from local based on/where category data */
    fun getFilterMealsLocal(mealName: String): List<com.shapeide.rasadesa.core.domain.FilterMeal>
}