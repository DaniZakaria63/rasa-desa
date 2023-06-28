package com.shapeide.rasadesa.databases.area

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.domains.Area
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.AreaModel
import com.shapeide.rasadesa.utills.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AreaRepository @Inject constructor(
    private val roomDB: RoomDB,
    private val apiEndpoint: APIEndpoint,
    private val context: Context
) {
    val _areaData: LiveData<List<Area>> = roomDB.areaDao.findAll().map {
        it.asDomainModel()
    }.asLiveData()

    suspend fun syncArea() {
        if (isOnline(context)) {
            Log.d(TAG, "syncArea: AreaRepository: refresh data from internet")
            val newData: ResponseMeals<AreaModel> = apiEndpoint.getArea("list")
            insertArea(newData.asDatabaseModel())
        }
    }

    suspend fun insertArea(newData: List<AreaEntity>) {
        Log.d(TAG, "insertArea: AreaRepository: insert all data to local")
        roomDB.areaDao.insertAll(newData)
    }

    //TODO: delete all data, for reinitialize
    suspend fun deleteArea() {
        Log.d(TAG, "deleteArea: AreaRepository: delete all data from local")
        roomDB.areaDao.deleteAll()
    }
}