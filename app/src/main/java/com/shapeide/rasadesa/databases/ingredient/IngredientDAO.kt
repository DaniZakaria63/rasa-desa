package com.shapeide.rasadesa.databases.ingredient

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/* tableName = tbl_ingredient */

@Dao
interface IngredientDAO {
    @Query("SELECT * FROM tbl_ingredient")
    fun findAll() : Flow<List<IngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<IngredientEntity>)

    @Query("DELETE FROM tbl_ingredient")
    suspend fun deleteAll()
}