package com.shapeide.rasadesa.viewmodels

import androidx.lifecycle.*
import com.shapeide.rasadesa.databases.meal.MealRepository
import com.shapeide.rasadesa.domains.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(private val mealRepository: MealRepository) : ViewModel() {

    val favoriteAllData: LiveData<List<Meal>> = mealRepository._favoriteData

}
