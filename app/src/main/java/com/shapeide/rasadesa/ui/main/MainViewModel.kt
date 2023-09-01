package com.shapeide.rasadesa.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.area.AreaRepository
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.filtermeal.FilterMealRepository
import com.shapeide.rasadesa.databases.ingredient.IngredientRepository
import com.shapeide.rasadesa.databases.meal.MealRepository
import com.shapeide.rasadesa.domains.Area
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.domains.Ingredient
import com.shapeide.rasadesa.domains.Meal
import com.shapeide.rasadesa.ui.listener.NetworkState
import com.shapeide.rasadesa.utills.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val filterMealRepository: FilterMealRepository,
    private val categoryRepository: CategoryRepository,
    mealRepository: MealRepository,
    private val areaRepository: AreaRepository,
    private val ingredientRepository: IngredientRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    val categoryData: LiveData<List<Category>> = categoryRepository._categoryData
    val filterMealData: LiveData<List<FilterMeal>> = filterMealRepository.filterMealData
    val favoriteMealData: LiveData<List<Meal>> = mealRepository._favoriteData
    val areaData: LiveData<List<Area>> = areaRepository._areaData
    val ingredientData: LiveData<List<Ingredient>> =ingredientRepository._ingredientData

    private val _selectedMealCategory = MutableLiveData("Beef")
    val selectedMealCategory: LiveData<String> get() = _selectedMealCategory

    private val _networkState = MutableLiveData(NetworkState.LOADING)
    val networkState: LiveData<NetworkState> get() = _networkState

    init {
        viewModelScope.launch(dispatcherProvider.main) {
            try {
                categoryRepository.syncCategory()
                syncFilterMealByMealName("Beef") //The beginning
                _networkState.value = NetworkState.SUCCESS
            } catch (networkError: IOException) {
                _networkState.value = NetworkState.ERROR
            }
        }
    }

    /* For giving the data based on clicked meal category */
    fun syncFilterMealByMealName(mealName: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            filterMealRepository.syncByMeal(mealName)
            _selectedMealCategory.value = mealName
        }
    }

    fun syncDiscovery() {
        viewModelScope.launch(dispatcherProvider.main) {
            areaRepository.syncArea()
            categoryRepository.syncCategory()
            ingredientRepository.syncIngredient()
        }
    }

    fun deleteLocalCategory() {
        viewModelScope.launch(dispatcherProvider.main) {
            Log.d(TAG, "deleteLocalCategory: Delete all the datas")
            categoryRepository.deleteLocalCategory()
        }
    }
}