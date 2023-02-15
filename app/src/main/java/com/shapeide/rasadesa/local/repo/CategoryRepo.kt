package com.shapeide.rasadesa.local.repo

import androidx.lifecycle.LiveData
import com.shapeide.rasadesa.local.dao.CategoryDAO
import com.shapeide.rasadesa.local.entity.Category

class CategoryRepo(private val categoryDAO: CategoryDAO) {
    val getAllData : LiveData<List<Category>> = categoryDAO.findAll()

    suspend fun addOne(category: Category){
        categoryDAO.addOne(category)
    }
}