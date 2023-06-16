package com.shapeide.rasadesa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.databases.area.AreaRepository
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.ingredient.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverVM @Inject constructor(
    private val areaRepository: AreaRepository,
    private val categoryRepository: CategoryRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    val areaData = areaRepository._areaData
    val categoryData = categoryRepository._categoryData
    val ingredientData =ingredientRepository._ingredientData

        init {
            viewModelScope.launch {
                areaRepository.syncArea()
                categoryRepository.syncCategory()
                ingredientRepository.syncIngredient()
            }
        }
}