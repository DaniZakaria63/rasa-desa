package com.shapeide.rasadesa.data.source

import com.shapeide.rasadesa.domain.Meal

interface MealDataSource {
    suspend fun getRandomMeal(): Meal

    suspend fun getRandomMealLocal(): Meal

    suspend fun getMealByID(id: Int): Meal?
    fun getOneMealLocal(id: Int): Meal?
    fun setOneMealLocal(meal: Any?)
    fun deleteAllMeal()
}