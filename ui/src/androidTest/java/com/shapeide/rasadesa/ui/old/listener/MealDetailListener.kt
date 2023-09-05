package com.shapeide.rasadesa.ui.old.listener

interface MealDetailListener {
    fun onNeedIntent(key: String, value: String, name: String)
    fun onDetailMeal(type: String, idMeal: Int)
}