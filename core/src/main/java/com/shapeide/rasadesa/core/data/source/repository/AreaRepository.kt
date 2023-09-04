package com.shapeide.rasadesa.core.data.source.repository

interface AreaRepository {
    suspend fun syncArea()
    suspend fun deleteArea()
}