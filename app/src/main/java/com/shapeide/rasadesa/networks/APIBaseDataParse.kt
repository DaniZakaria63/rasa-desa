package com.shapeide.rasadesa.networks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

abstract class APIBaseDataParse {
    protected suspend inline fun <T : Any> getResultAPI(
        type: Type,
        crossinline call: suspend () -> Response<T>
    ) : Flow<ResultAPI<T>> {
        return flow {
            emit(ResultAPI.InProgress(true))

            val response = call()

            emit(
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null) ResultAPI.Success(body, type)
                    else ResultAPI.UnknownError(null)
                }else ResultAPI.ApiError(response.errorBody()!!, response.code())
            )

            emit(ResultAPI.InProgress(false))
        }.catch { e->
            emit(ResultAPI.InProgress(false))
            emit(if(e is IOException) ResultAPI.NetworkError(e) else ResultAPI.UnknownError(e))
        }.flowOn(Dispatchers.IO)
    }
}