package com.shapeide.rasadesa.room.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/* tableName = tbl_search */
@Dao
interface MealSearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneSearch(meal: MealSearchEntity)

    @Query("SELECT * FROM tbl_search")
    fun findAll() : Flow<List<MealSearchEntity>>

    @Query("SELECT * FROM tbl_search WHERE search LIKE '%' || :searchQuery || '%'")
    fun querySearch(searchQuery: String) : List<MealSearchEntity>

    @Query("DELETE FROM tbl_search WHERE id_ori = :searchId")
    fun deleteOne(searchId: String)

    @Query("DELETE FROM tbl_search")
    fun deleteAll()
}