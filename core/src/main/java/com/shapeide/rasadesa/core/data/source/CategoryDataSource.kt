package com.shapeide.rasadesa.core.data.source

interface CategoryDataSource {
    suspend fun syncCategory()

    suspend fun deleteLocalCategory()
    fun insertCategory(datas: List<Any>)
}