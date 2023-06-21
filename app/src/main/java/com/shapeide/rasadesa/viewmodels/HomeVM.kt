package com.shapeide.rasadesa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.filtermeal.FilterMealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(val filterMealRepository: FilterMealRepository, val categoryRepository: CategoryRepository): ViewModel() {

    val categoryData = categoryRepository._categoryData
    val filterMealData = filterMealRepository.filterMealData

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
            filterMealRepository.syncByMeal(mealName)
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