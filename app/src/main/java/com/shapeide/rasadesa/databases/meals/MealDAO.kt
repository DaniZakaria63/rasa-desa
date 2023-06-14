package com.shapeide.rasadesa.databases.meals

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/* tableName = tbl_filter_meals */
@Dao
interface MealDAO {
    @Query("SELECT * FROM tbl_filter_meals")
    fun findAll_FM() : LiveData<List<FilterMealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll_FM(meals: List<FilterMealEntity>)

    @Query("SELECT * FROM tbl_filter_meals WHERE  type = :meal")
    fun findByName_FM(meal: String?) : List<FilterMealEntity>

    @Query("DELETE FROM tbl_filter_meals")
    fun deleteAll_FM()
}