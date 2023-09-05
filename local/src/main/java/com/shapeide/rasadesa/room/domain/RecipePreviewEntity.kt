package com.shapeide.rasadesa.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_recipe_preview")
data class RecipePreviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("_id") var _id: String? = null,
    @ColumnInfo("uri") var uri: String? = null,
    @ColumnInfo("label") var label: String? = null,
    @ColumnInfo("image") var image: String? = null,
    @ColumnInfo("meal_type") var mealType: String? = null,
    @ColumnInfo("calories") var calories: Double? = null,
    @ColumnInfo("total_time") var totalTime: Int? = null,
    @ColumnInfo("diets") var diets: List<String> = listOf(),
)