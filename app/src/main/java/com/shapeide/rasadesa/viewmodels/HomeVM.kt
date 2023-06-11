package com.shapeide.rasadesa.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.utills.RasaApplication
import kotlinx.coroutines.launch
import java.io.IOException

class HomeVM(_application: Application) : AndroidViewModel(_application) {
    private val application = (_application as RasaApplication)
    private val categoryRepository = application.categoryRepository
    val categoryData = categoryRepository._categoryData

    private val mealRepository = application.mealRepository
    val filterMealData = mealRepository._filterMealData

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
                // check for internet connection first,
                // then if its disconnected just load the local data
                categoryRepository.syncCategory()
                _isNetworkEventError.value = false
                _isNetworkGeneralError.value = false
            } catch (networkError: IOException) {
                _isNetworkEventError.value = true
            }
        }
    }

    /* For giving the data based on clicked meal category */
    fun syncFilterMealByMealName(mealName: String) {
        viewModelScope.launch {
            Log.d(TAG, "syncFilterMealByMealName: Start sync filter meal result")
            try {
                mealRepository.syncByMeal(mealName)
                _isNetworkEventError.value = false
                _isNetworkGeneralError.value = false
            } catch (networkError: IOException) {
                _isNetworkEventError.value = true
            }
        }
    }

    fun updateMealFilter(mealName: String){
        viewModelScope.launch {
            _selectedMealCategory.value = mealName
            syncFilterMealByMealName(mealName)
        }
    }

    fun deleteLocalCategory() {
        viewModelScope.launch {
            Log.d(TAG, "deleteLocalCategory: Delete all the datas")
            categoryRepository.deleteLocalCategory()
        }
    }
}