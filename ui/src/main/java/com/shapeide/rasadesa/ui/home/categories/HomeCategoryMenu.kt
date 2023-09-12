package com.shapeide.rasadesa.ui.home.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.ui.graphics.vector.ImageVector
import com.shapeide.rasadesa.domain.domain.MealType

enum class HomeCategoryMenu(val title: String, val icon: ImageVector) {
    BREAKFAST(MealType.Breakfast.name, Icons.Filled.Fastfood),
    DINNER(MealType.Dinner.name, Icons.Filled.BrunchDining),
    LUNCH(MealType.Lunch.name, Icons.Filled.LunchDining),
}