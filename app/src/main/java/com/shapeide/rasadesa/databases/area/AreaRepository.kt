package com.shapeide.rasadesa.databases.area

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.domains.Area
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.AreaModel
import com.shapeide.rasadesa.utills.DispatcherProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AreaRepository @Inject constructor(
    private val desaDatabase: DesaDatabase,
    private val apiEndpoint: APIEndpoint,
    private val dispatcherProvider: DispatcherProvider
) {
    val _areaData: LiveData<List<Area>> = desaDatabase.areaDao.findAll().map {
        it.asDomainModel()
    }.asLiveData()

    suspend fun syncArea() {
        coroutineScope {
            launch(dispatcherProvider.io) {
                try {
                    val newData: ResponseMeals<AreaModel> = apiEndpoint.getArea("list")
                    insertArea(newData.asDatabaseModel())
                } catch (e: Exception) {
                    Log.d(TAG, "syncArea: Debug Log Network Error", e)
                }
            }
        }
    }


    private suspend fun insertArea(newData: List<AreaEntity>) {
        coroutineScope {
            launch(dispatcherProvider.io) { desaDatabase.areaDao.insertAll(newData) }
        }
    }


    suspend fun deleteArea() {
        coroutineScope {
            launch(dispatcherProvider.io) { desaDatabase.areaDao.deleteAll() }
        }
    }
}