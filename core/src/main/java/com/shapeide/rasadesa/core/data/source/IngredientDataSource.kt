package com.shapeide.rasadesa.core.data.source

interface IngredientDataSource {
    suspend fun syncIngredient()

    suspend fun insertAll(datas: List<Any>)

    suspend fun deleteAll()
}