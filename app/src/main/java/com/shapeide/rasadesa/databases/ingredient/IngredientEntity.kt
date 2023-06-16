package com.shapeide.rasadesa.databases.ingredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domains.Ingredient
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.IngredientsModel

@Entity(tableName = "tbl_ingredient")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "_id") val idIngredient: Int,
    @ColumnInfo(name = "name") val strIngredient: String,
    @ColumnInfo(name = "description") val strDescription: String?,
    @ColumnInfo(name = "type") val strType: String? = "default"
)

fun List<IngredientEntity>.asDomainModel() : List<Ingredient> {
    return map {
        Ingredient(
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
