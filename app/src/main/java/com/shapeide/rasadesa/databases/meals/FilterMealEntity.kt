package com.shapeide.rasadesa.databases.meals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.FilterMealModel

@Entity(tableName = "tbl_filter_meals")
data class FilterMealEntity constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "_id") val idMeal : Int,
    @ColumnInfo(name = "name") val strMeal: String,
    @ColumnInfo(name = "image_thumb_url") val strMealThumb: String,
    @ColumnInfo(name = "type") val strType : String,
)

fun List<FilterMealEntity>.entityAsDomainModel() : List<FilterMeal> {
    return map{
        FilterMeal(
            id = it.idMeal,
            name = it.strMeal,
            thumb = it.strMealThumb,
            type = it.strType
        )
    }
}

fun ResponseMeals<FilterMealModel>.asDomainModel() : List<FilterMeal>{
    return meals.map {
        FilterMeal(
            id = it.idMeal,
            name = it.strMeal,
            thumb = it.strMealThumb,
            type = it.typeMeal
        )
    }
}

fun ResponseMeals<FilterMealModel>.asDatabaseModel() : List<FilterMealEntity>{
    return meals.map {
        FilterMealEntity(
            id = it.idMeal,
            idMeal = it.idMeal,
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb,
            strType = it.typeMeal
        )
    }
}