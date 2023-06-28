package com.shapeide.rasadesa.databases.meal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shapeide.rasadesa.domains.Meal
import kotlinx.coroutines.flow.Flow

/* tableName = tbl_meals */
@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneMeal(meal: MealEntity)

    @Query("SELECT * FROM tbl_meals ORDER BY RANDOM() LIMIT 1")
    suspend fun findRandomOne(): MealEntity

    @Query("SELECT * FROM tbl_meals WHERE id_meal = :id")
    suspend fun findOne(id: Int) : MealEntity?

    @Query("DELETE FROM tbl_meals")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_meals WHERE is_favorite = 1")
    fun allFavorite(): Flow<List<MealEntity>>
}