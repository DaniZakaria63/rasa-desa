package com.shapeide.rasadesa.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.local.entity.CategoryEntity
import com.shapeide.rasadesa.local.RoomRepository
import com.shapeide.rasadesa.utills.RasaApplication
import kotlinx.coroutines.launch
import java.io.IOException

class HomeVM(application: RasaApplication) : AndroidViewModel(application) {
    /*
    private val _categoryEntity = MutableLiveData<List<CategoryEntity>>()
            val categoryData : LiveData<List<CategoryEntity>>
            get() = _categoryEntity
     */

    private val roomRepository = RoomRepository(application.database, application.apiEndpoint)
    val categoryData = roomRepository._categoryData

    private val _isNetworkEventError = MutableLiveData(false)
    val isNetworkEventError: LiveData<Boolean> get() = _isNetworkEventError

    private val _isNetworkGeneralError = MutableLiveData(false)
    val isNetworkGeneralError: LiveData<Boolean> get() = _isNetworkGeneralError

    init {
        viewModelScope.launch {
            Log.d(TAG, "HomeVM : Initialized ViewModel")
            try {
                roomRepository.syncCategory()
                _isNetworkEventError.value = false
                _isNetworkGeneralError.value = false
            } catch (networkError: IOException) {
                _isNetworkEventError.value = true
            }
        }
    }

    fun getLocalCategory(){
        viewModelScope.launch {
            val localData = roomRepository.getLocalCategory()
            Log.d(TAG, "getLocalCategory: one of the local data = {${localData.value}}")
        }
    }

    fun deleteLocalCategory(){
        viewModelScope.launch {

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