package com.shapeide.rasadesa.databases.area

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/* tableName : tbl_area */
@Dao
interface AreaDAO {
    @Query("SELECT * FROM tbl_area")
    fun findAll() : Flow<List<AreaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(datas: List<AreaEntity>)

    @Query("DELETE FROM tbl_area")
    fun deleteAll()
}