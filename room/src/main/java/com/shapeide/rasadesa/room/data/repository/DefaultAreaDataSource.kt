package com.shapeide.rasadesa.room.data.repository

import com.shapeide.rasadesa.core.coroutines.DefaultDispatcherProvider
import com.shapeide.rasadesa.room.data.source.AreaDao
import com.shapeide.rasadesa.core.data.source.room.AreaDataSource
import com.shapeide.rasadesa.room.domain.AreaEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Implemented Area Data Source
 * by Room
 * */
class DefaultAreaDataSource(
    private val areaDao: AreaDao,
    private val dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DefaultDispatcherProvider
) : com.shapeide.rasadesa.core.data.source.room.AreaDataSource {
    override suspend fun deleteArea() {
        coroutineScope {
            launch(dispatcherProvider.io) { areaDao.deleteAll() }
        }
    }

    override suspend fun insertArea(newData: List<AreaEntity>) {
        coroutineScope {
            launch(dispatcherProvider.io) {
                areaDao.insertAll(newData)
            }
        }
    }
}