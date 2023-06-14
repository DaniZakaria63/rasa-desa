package com.shapeide.rasadesa.databases.meals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domains.FilterMeal

@Entity(tableName = "tbl_filter_meals")
data class FilterMealEntity constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "_id") val idMeal : Int,
    @ColumnInfo(name = "name") val strMeal: String,
    @ColumnInfo(name = "image_thumb_url") val strMealThumb: String,
    @ColumnInfo(name = "type") val strType : String,
)

fun List<FilterMealEntity>.asDomainModel() : List<FilterMeal> {
    return map{
        FilterMeal(
            id = it.idMeal,
            name = it.strMeal,
            thumb = it.strMealThumb,
            type = it.strType
        )
    }
}