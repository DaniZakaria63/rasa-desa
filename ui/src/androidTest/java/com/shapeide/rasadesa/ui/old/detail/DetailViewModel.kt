package com.shapeide.rasadesa.ui.old.detail

import androidx.lifecycle.*
import com.shapeide.rasadesa.core.data.repository.MealRepository
import com.shapeide.rasadesa.core.domain.Meal
import kotlinx.coroutines.launch

/*
 * there is two changes for the type, for mealId or random meal
 * both have similar response of json
 *  */

class DetailViewModel constructor(
    mType: String,
    private val mMealID: Int,
    private val mealRepository: com.shapeide.rasadesa.core.data.repository.MealRepository
) : ViewModel() {
    private val _mealData = MutableLiveData<com.shapeide.rasadesa.core.domain.Meal?>()
    val mealData: MutableLiveData<com.shapeide.rasadesa.core.domain.Meal?> get() = _mealData

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
        private val mealRepository: com.shapeide.rasadesa.core.data.repository.MealRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(DetailViewModel::class.java))
                return DetailViewModel(type.toString(), id, mealRepository) as T

            throw IllegalArgumentException("Unknown ViewModel Class, #1")
        }
    }
}