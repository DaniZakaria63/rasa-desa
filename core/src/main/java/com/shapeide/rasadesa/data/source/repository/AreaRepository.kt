package com.shapeide.rasadesa.data.source.repository

interface AreaRepository {
    suspend fun syncArea()
    suspend fun deleteArea()
}