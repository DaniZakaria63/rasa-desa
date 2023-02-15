package com.shapeide.rasadesa.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shapeide.rasadesa.local.entity.Category

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOne(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAll(categories: List<Category>)

    @Query(value = "SELECT * FROM tbl_category ORDER BY id DESC")
    fun findAll() : LiveData<List<Category>>

}
