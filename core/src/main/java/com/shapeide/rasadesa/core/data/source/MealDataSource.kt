package com.shapeide.rasadesa.core.data.source

import com.shapeide.rasadesa.core.domain.Meal

interface MealDataSource {
    suspend fun getRandomMeal(): com.shapeide.rasadesa.core.domain.Meal

    suspend fun getRandomMealLocal(): com.shapeide.rasadesa.core.domain.Meal

    suspend fun getMealByID(id: Int): com.shapeide.rasadesa.core.domain.Meal?
    fun getOneMealLocal(id: Int): com.shapeide.rasadesa.core.domain.Meal?
    fun setOneMealLocal(meal: Any?)
    fun deleteAllMeal()
}