package com.shapeide.rasadesa.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.AreaModel

@Entity(tableName = "tbl_area")
data class AreaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val strArea: String,
)

fun com.shapeide.rasadesa.data.ResponseMeals<AreaModel>.asDatabaseModel() : List<AreaEntity>{
    return meals.map {
        AreaEntity(
            strArea = it.strArea
        )
    }
}
