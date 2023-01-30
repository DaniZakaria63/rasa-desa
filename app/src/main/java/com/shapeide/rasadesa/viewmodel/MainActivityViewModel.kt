package com.shapeide.rasadesa.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.networks.APIHelper
import com.shapeide.rasadesa.networks.ResultAPI
import com.shapeide.rasadesa.utills.DataManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(val dataManager: DataManager): ViewModel() {

    private val _result = MutableLiveData<ResultAPI<Any>>()
    val result: LiveData<ResultAPI<Any>> get() = _result

    fun exectNetworkCall(codeBlock: suspend () -> Flow<ResultAPI<Any>>) {
        viewModelScope.launch {
            codeBlock().collect(){ result ->
                _result.value = result
                when(result){
                    is ResultAPI.ApiError -> Log.e(TAG, result.errorBody.toString())
                    is ResultAPI.NetworkError -> Log.e(TAG, result.error.toString(), result.error)
                    is ResultAPI.UnknownError -> Log.e(TAG, result.exception.toString(), result.exception)
                }
            }
        }
    }

    fun getRandomMeal(){
        exectNetworkCall {
            dataManager.apiHelper.callGetRandomMeal()
        }
    }
}