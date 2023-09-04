package com.shapeide.rasadesa.presenter.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.data.repository.FilterMealRepository
import com.shapeide.rasadesa.ui.listener.NetworkState
import com.shapeide.rasadesa.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val filterMealRepository: com.shapeide.rasadesa.data.repository.FilterMealRepository,
    private val categoryRepository: com.shapeide.rasadesa.data.repository.CategoryRepository,
    mealRepository: com.shapeide.rasadesa.data.repository.MealRepository,
    private val defaultAreaRepository: com.shapeide.rasadesa.data.repository.DefaultAreaRepository,
    private val ingredientRepository: com.shapeide.rasadesa.data.repository.IngredientRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    val categoryData: LiveData<List<com.shapeide.rasadesa.domain.Category>> = categoryRepository._categoryData
    val filterMealData: LiveData<List<com.shapeide.rasadesa.domain.FilterMeal>> = filterMealRepository.filterMealData
    val favoriteMealData: LiveData<List<com.shapeide.rasadesa.domain.Meal>> = mealRepository._favoriteData
    val areaData: LiveData<List<com.shapeide.rasadesa.domain.Area>> = defaultAreaRepository._areaData
    val ingredientData: LiveData<List<com.shapeide.rasadesa.domain.Ingredient>> =ingredientRepository._ingredientData

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
            defaultAreaRepository.syncArea()
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