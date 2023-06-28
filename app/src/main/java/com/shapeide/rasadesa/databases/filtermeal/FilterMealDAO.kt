package com.shapeide.rasadesa.databases.filtermeal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/* tableName = tbl_filter_meals */
@Dao
interface FilterMealDAO {
    @Query("SELECT * FROM tbl_filter_meals")
    fun findAll_FM() : LiveData<List<FilterMealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll_FM(meals: List<FilterMealEntity>)

    @Query("SELECT * FROM tbl_filter_meals WHERE  type = :meal")
    suspend fun findByName_FM(meal: String?) : List<FilterMealEntity>

    @Query("DELETE FROM tbl_filter_meals")
    suspend fun deleteAll_FM()
}