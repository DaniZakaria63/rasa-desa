package com.shapeide.rasadesa.databases.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/* tableName : tbl_category */
@Dao
interface CategoryDAO {
    @Query("SELECT * FROM tbl_category")
    fun findAll() : Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOne(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryEntity>)

    @Query("DELETE FROM tbl_category")
    fun deleteAll()

}
