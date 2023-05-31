package com.shapeide.rasadesa.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shapeide.rasadesa.local.entity.CategoryEntity

@Dao
interface CategoryDAO {
    @Query("select * from tbl_category")
    fun findAll() : LiveData<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOne(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryEntity>)

    @Query("delete from tbl_category")
    fun deleteAll()

}
