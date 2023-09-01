package com.shapeide.rasadesa.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.search.asDatabaseModel
import com.shapeide.rasadesa.domains.Search
import com.shapeide.rasadesa.ui.search.SearchRoomManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val roomManager: SearchRoomManager) : ViewModel() {
    private val allSearchHistoryData = roomManager._searchHistoryData

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _mealSearchData = MutableLiveData<List<Search>>()
    val mealSearchData: LiveData<List<Search>> get() = _mealSearchData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _errorMessage.value = "error happened, try again"
        throwable.printStackTrace()
    }

    fun searchHistoryData() {
        viewModelScope.launch {
            _mealSearchData.postValue(allSearchHistoryData.value)
            if (mealSearchData.value?.isEmpty() == true) _errorMessage.value = "there is no history"
        }
    }

    fun queryMealSearch(query: String = "") {
        viewModelScope.launch(exceptionHandler) {
            val searchData: List<Search> = roomManager.queryMealSearch(query)
            _mealSearchData.postValue(searchData)
        }
    }

    fun addMealSearch(search: Search) {
        viewModelScope.launch(exceptionHandler) {
            roomManager.saveMealSearch(search.asDatabaseModel())
            Log.d(TAG, "addMealSearch: done local saving")
        }
    }

    fun deleteMealSearch(search: Search) {
        viewModelScope.launch(exceptionHandler) {
            roomManager.deleteMealSearch(search.id)
            Log.d(TAG, "deleteMealSearch: done remove search history")
        }
    }

    /* This are AppSearch ways, ended up broken
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
     */
}