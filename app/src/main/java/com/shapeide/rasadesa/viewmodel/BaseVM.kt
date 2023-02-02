package com.shapeide.rasadesa.viewmodel

import androidx.lifecycle.ViewModel
import com.shapeide.rasadesa.networks.ResponseCategory
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.utills.Resource
import retrofit2.Response

abstract class BaseVM : ViewModel(){
    @Suppress("UNCHECKED_CAST")
    protected fun <T>handleResponse(type: String, response: Response<T>) : Resource<T> {
        if(response.isSuccessful){
            if(type.equals(MainActivityVM.TYPE_MEALS)){
                val parsedResponse = response as Response<ResponseMeals<T>>
                parsedResponse.body()?.meals.let { data->
                    return Resource.Success(data!!) as Resource<T>
                }
            }else if(type.equals(MainActivityVM.TYPE_CATEGORIES)){
                val parsedResponse = response as Response<ResponseCategory<T>>
                parsedResponse.body()?.categories.let { data ->
                    return Resource.Success(data!!) as Resource<T>
                }
            }else{
                throw IllegalArgumentException("Kategori tidak ada")
            }
        }
        return Resource.Error(response.message())
    }

}