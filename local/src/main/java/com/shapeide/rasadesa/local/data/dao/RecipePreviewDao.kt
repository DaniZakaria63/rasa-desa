package com.shapeide.rasadesa.local.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shapeide.rasadesa.local.domain.RecipePreviewEntity
import kotlinx.coroutines.flow.Flow

/* tableName : tbl_recipe_preview */
@Dao
interface RecipePreviewDao {
    @Query("SELECT * FROM tbl_recipe_preview")
    fun findAll() : Flow<List<RecipePreviewEntity>>

    @Query("SELECT * FROM tbl_recipe_preview WHERE meal_type=:type")
    fun findByMealType(type: String) : Flow<List<RecipePreviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<RecipePreviewEntity>)
}