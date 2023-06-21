package com.shapeide.rasadesa.viewmodels

import androidx.lifecycle.*
import com.shapeide.rasadesa.databases.filtermeal.FilterMealRepository
import com.shapeide.rasadesa.databases.meal.MealRepository
import com.shapeide.rasadesa.domains.Meal
import com.shapeide.rasadesa.ui.fragments.DetailFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

/*
 * there is two changes for the type, for mealId or random meal
 * both have similar response of json
 *  */

class DetailVM constructor(
    mType: String,
    private val mMealID: Int,
    private val mealRepository: MealRepository
) : ViewModel() {
    private val _mealData = MutableLiveData<Meal?>()
    val mealData: MutableLiveData<Meal?> get() = _mealData

    init {
        if (mType.equals(DetailFragment.VAL_TYPE_RANDOM)) {
            randomMeal()
        } else syncMealID()
    }

    private fun randomMeal() {
        viewModelScope.launch {
            val randomMeal = mealRepository.getRandomMeal()
            _mealData.postValue(randomMeal)
        }
    }

    private fun syncMealID() {
        viewModelScope.launch {
            val mealData = mealRepository.getMealByID(mMealID)
            _mealData.postValue(mealData)
        }
    }

    class DetailVMFactory constructor(
        val type: String?,
        val id: Int,
        private val mealRepository: MealRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(DetailVM::class.java))
                return DetailVM(type.toString(), id, mealRepository) as T

            throw IllegalArgumentException("Unknown ViewModel Class, #1")
        }
    }
}