package com.shapeide.rasadesa.databases.search

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shapeide.rasadesa.domains.Search
import kotlinx.coroutines.flow.Flow

/* tableName = tbl_search */
@Dao
interface MealSearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneSearch(meal: MealSearchEntity)

    @Query("SELECT * FROM tbl_search")
    fun findAll() : Flow<List<MealSearchEntity>>

    @Query("SELECT * FROM tbl_search WHERE search LIKE '%' || :searchQuery || '%'")
    suspend fun querySearch(searchQuery: String) : List<MealSearchEntity>

    @Query("DELETE FROM tbl_search WHERE id_ori = :searchId")
    suspend fun deleteOne(searchId: String)

    @Query("DELETE FROM tbl_search")
    suspend fun deleteAll()
}