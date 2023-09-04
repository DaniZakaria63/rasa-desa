package com.shapeide.rasadesa.interactors

import com.shapeide.rasadesa.data.repository.MealRepository

class GetMealById constructor(val repository: MealRepository) {
    suspend operator fun invoke(id: Int){
        repository.getMealByID(id)
    }
}