package com.shapeide.rasadesa.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.utills.RasaApplication
import kotlinx.coroutines.launch
import java.io.IOException

class HomeVM(application: RasaApplication) : AndroidViewModel(application) {
    private val categoryRepository = CategoryRepository(application.database, application.apiEndpoint)
    val categoryData = categoryRepository._categoryData

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

    fun deleteLocalCategory(){
        viewModelScope.launch {
            Log.d(TAG, "deleteLocalCategory: Delete all the datas")
            categoryRepository.deleteLocalCategory()
        }
    }

    class HomeFactoryVM(private val application: RasaApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeVM(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class, #1")
        }
    }
}