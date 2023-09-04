package com.shapeide.rasadesa.core.interactors

import com.shapeide.rasadesa.core.data.repository.MealRepository

class GetMealById constructor(val repository: com.shapeide.rasadesa.core.data.repository.MealRepository) {
    suspend operator fun invoke(id: Int){
        repository.getMealByID(id)
    }
}