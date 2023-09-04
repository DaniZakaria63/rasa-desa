package com.shapeide.rasadesa.core.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.core.coroutines.DefaultDispatcherProvider
import com.shapeide.rasadesa.core.data.source.room.AreaDataSource
import com.shapeide.rasadesa.core.data.source.repository.AreaRepository
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.AreaModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultAreaRepository(
    private val dataSource: com.shapeide.rasadesa.core.data.source.room.AreaDataSource,
    private val
) : com.shapeide.rasadesa.core.data.source.repository.AreaRepository {
    private val dispatcherProvider =
        com.shapeide.rasadesa.core.coroutines.DefaultDispatcherProvider()

    // This definitely repository
    override suspend fun syncArea() {
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


    // This data source
    override suspend fun insertArea(newData: List<AreaEntity>) {
        coroutineScope {
            launch(dispatcherProvider.io) { desaDatabase.areaDao.insertAll(newData) }
        }
    }


    // This for both
    override suspend fun deleteArea() {
        coroutineScope {
            launch(dispatcherProvider.io) { desaDatabase.areaDao.deleteAll() }
        }
    }
}