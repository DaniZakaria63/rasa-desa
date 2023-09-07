package com.shapeide.rasadesa.ui.home.categories

import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.ui.R

enum class HomeCategoryMenu(val title: String, val icon: Int) {
    BREAKFAST(MealType.Breakfast.name, R.drawable.ic_outline_home),
    DINNER(MealType.Dinner.name, R.drawable.ic_outline_compass),
    LUNCH(MealType.Lunch.name, R.drawable.ic_outline_favorite),
}