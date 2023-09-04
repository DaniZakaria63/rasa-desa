package com.shapeide.rasadesa.data.source.room

import com.shapeide.rasadesa.room.domain.AreaEntity

interface AreaDataSource {
    suspend fun deleteArea()
    suspend fun insertArea(newData: List<AreaEntity>)
}