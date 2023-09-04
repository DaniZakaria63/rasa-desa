package com.shapeide.rasadesa.room.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shapeide.rasadesa.room.domain.AreaEntity
import kotlinx.coroutines.flow.Flow

/* tableName : tbl_area */
@Dao
interface AreaDao {
    @Query("SELECT * FROM tbl_area")
    fun findAll() : Flow<List<AreaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(datas: List<AreaEntity>)

    @Query("DELETE FROM tbl_area")
    fun deleteAll()
}