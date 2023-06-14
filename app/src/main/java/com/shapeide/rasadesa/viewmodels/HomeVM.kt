package com.shapeide.rasadesa.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.utills.RasaApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(val mealRepository: MealRepository, val categoryRepository: CategoryRepository): ViewModel() {

    val categoryData = categoryRepository._categoryData
    val filterMealData = mealRepository.filterMealData

    private val _selectedMealCategory = MutableLiveData("Beef")
    val selectedMealCategory: LiveData<String> get() = _selectedMealCategory

    private val _isNetworkEventError = MutableLiveData(false)
    val isNetworkEventError: LiveData<Boolean> get() = _isNetworkEventError

    private val _isNetworkGeneralError = MutableLiveData(false)
    val isNetworkGeneralError: LiveData<Boolean> get() = _isNetworkGeneralError

    init {
        viewModelScope.launch {
            Log.d(TAG, "HomeVM : Initialized ViewModel")
            try {
                categoryRepository.syncCategory()
                syncFilterMealByMealName("Beef") //The beginning
                _isNetworkEventError.value = false
                _isNetworkGeneralError.value = false
            } catch (networkError: IOException) {
                _isNetworkEventError.value = true
            }
        }
    }

    /* For giving the data based on clicked meal category */
    fun syncFilterMealByMealName(mealName: String) {
        Log.d(TAG, "syncFilterMealByMealName: Start sync filter meal result")
        viewModelScope.launch {
            mealRepository.syncByMeal(mealName)
            _selectedMealCategory.value = mealName
            _isNetworkEventError.value = false
            _isNetworkGeneralError.value = false
        }
    }

    fun deleteLocalCategory() {
        viewModelScope.launch {
            Log.d(TAG, "deleteLocalCategory: Delete all the datas")
            categoryRepository.deleteLocalCategory()
        }
    }
}