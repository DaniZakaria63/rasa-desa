package com.shapeide.rasadesa.data.source

interface CategoryDataSource {
    suspend fun syncCategory()

    suspend fun deleteLocalCategory()
    fun insertCategory(datas: List<Any>)
}