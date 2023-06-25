package com.shapeide.rasadesa.viewmodels

import android.app.Application
import androidx.appsearch.app.SearchResult
import androidx.lifecycle.*
import com.shapeide.rasadesa.databases.meal.MealSearch
import com.shapeide.rasadesa.domains.Search
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.ui.search.SearchAppManager
import com.shapeide.rasadesa.utills.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchVM constructor(application: Application) :
    AndroidViewModel(application) {
    @Inject
    lateinit var apiEndpoint: APIEndpoint
    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _mealSearchData = MutableLiveData<List<Search>>()
    val mealSearchData: LiveData<List<Search>> get() = _mealSearchData

    private val searchAppManager: SearchAppManager =
        SearchAppManager(getApplication(), viewModelScope)

    fun queryMealSearch(query: String = "") {
        viewModelScope.launch {
            /* looking for the result online,
            *  if the device offline will be provided with AppSearch */
            if (isOnline(getApplication())) {
                val result: ResponseMeals<MealModel> = apiEndpoint.getSearchMeal(query)
                _mealSearchData.postValue(result.meals.toSearchModel())
            } else {
                val result = searchAppManager.queryMealSearch(query)
                _mealSearchData.postValue(result.toDomainModel())
            }
        }
    }

    fun addMealSearch(search: Search){
        viewModelScope.launch {
            val result = searchAppManager.addMealSearch(MealSearch(id = search.id, text = search.text))
            if(!result.isSuccess) _errorMessage.value = "Failed to save the data ${search.text}"

            queryMealSearch()
        }
    }

    fun deleteMealSearch(search: Search) {
        viewModelScope.launch {
            val result = searchAppManager.removeMealSearch(search.namespace, search.id)
            if (!result.isSuccess) _errorMessage.value = "Failed to delete ${search.text}"

            queryMealSearch()
        }
    }

    private fun List<SearchResult>.toDomainModel(): List<Search> {
        return map {
            val search = it.genericDocument.toDocumentClass(MealSearch::class.java)
            Search(id = search.id, text = search.text)
        }
    }

    private fun List<MealModel>.toSearchModel() : List<Search> {
        return map {
            Search(
                id = it.idMeal.toString(),
                text = it.strMeal.toString()
            )
        }
    }
}