package com.shapeide.rasadesa.networks

import okhttp3.ResponseBody
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type

sealed class ResultAPI<out R>{
    data class Success<T>(val data: T, val type: Type) : ResultAPI<T>()

    data class ApiError(val errorBody: ResponseBody, val code: Int) : ResultAPI<Nothing>()

    data class NetworkError(val error: IOException) : ResultAPI<Nothing>()

    data class UnknownError(val exception: Throwable?) : ResultAPI<Nothing>()

    data class InProgress(val isLoading: Boolean) : ResultAPI<Nothing>()
}
