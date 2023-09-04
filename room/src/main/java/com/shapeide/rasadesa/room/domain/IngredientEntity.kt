package com.shapeide.rasadesa.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domain.ResponseMeals
import com.shapeide.rasadesa.domain.IngredientsModel

@Entity(tableName = "tbl_ingredient")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "_id") val idIngredient: Int,
    @ColumnInfo(name = "name") val strIngredient: String,
    @ColumnInfo(name = "description") val strDescription: String?,
    @ColumnInfo(name = "type") val strType: String? = "default"
)

fun List<IngredientEntity>.asDomainModel() : List<com.shapeide.rasadesa.core.domain.Ingredient> {
    return map {
        com.shapeide.rasadesa.core.domain.Ingredient(
            idIngredient = it.idIngredient,
            strIngredient = it.strIngredient,
            strDescription = it.strDescription ?: "",
            strType = it.strType ?: "default"
        )
    }
}

fun ResponseMeals<IngredientsModel>.asDatabaseModel() : List<IngredientEntity>{
    return meals.map {
        IngredientEntity(
            idIngredient = it.idIngredient,
            strIngredient = it.strIngredient,
            strDescription = it.strDescription ?: "",
            strType = it.strType ?: "default"
        )
    }
}
