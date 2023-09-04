package com.shapeide.rasadesa.data.source

interface IngredientDataSource {
    suspend fun syncIngredient()

    suspend fun insertAll(datas: List<Any>)

    suspend fun deleteAll()
}