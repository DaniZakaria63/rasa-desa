package com.shapeide.rasadesa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.models.MealModel
import com.shapeide.rasadesa.networks.NetworkRepository
import com.shapeide.rasadesa.utills.Resource
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainActivityVM(val networkRepository: NetworkRepository) : BaseVM() {
    val randomMeal: MutableLiveData<Resource<MealModel>> = MutableLiveData()

    fun getRandomMeal() = viewModelScope.launch {
        randomMeal.postValue(Resource.Loading())
        val response = networkRepository.getRandomMeal()
        val parsedResponse =
            handleResponse(TYPE_MEALS, response.awaitResponse()) as Resource<MealModel>
        randomMeal.postValue(parsedResponse)
    }

    companion object {
        val TYPE_MEALS = "meals"
        val TYPE_CATEGORIES = "categories"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainActivityVM(NetworkRepository()) as T
            }
        }
    }
}