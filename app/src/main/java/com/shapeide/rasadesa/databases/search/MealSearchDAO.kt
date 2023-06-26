package com.shapeide.rasadesa.databases.search

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shapeide.rasadesa.domains.Search

/* tableName = tbl_search */
@Dao
interface MealSearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneSearch(meal: MealSearchEntity)

    @Query("SELECT * FROM tbl_search")
    fun findAll() : LiveData<List<MealSearchEntity>>

    @Query("SELECT * FROM tbl_search WHERE search LIKE '%' || :searchQuery || '%'")
    fun querySearch(searchQuery: String) : List<MealSearchEntity>

    @Query("DELETE FROM tbl_search WHERE id_ori = :searchId")
    fun deleteOne(searchId: String)

    @Query("DELETE FROM tbl_search")
    fun deleteAll()
}