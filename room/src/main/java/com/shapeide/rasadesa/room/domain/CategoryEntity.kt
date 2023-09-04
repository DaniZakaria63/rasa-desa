package com.shapeide.rasadesa.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domain.ResponseCategory
import com.shapeide.rasadesa.domain.CategoryModel

@Entity(tableName = "tbl_category")
data class CategoryEntity constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "_id") val idCategory: Int,
    @ColumnInfo(name = "name") val strCategory: String,
    @ColumnInfo(name = "image_thumb_url") val strCategoryThumb: String,
    @ColumnInfo(name = "image_desc") val strCategoryDescription: String,
)

/* Map from table data type into domain data type */
fun List<CategoryEntity>.asDomainModel(): List<com.shapeide.rasadesa.domain.Category> {
    return map {
        com.shapeide.rasadesa.domain.Category(
            id = it.idCategory,
            name = it.strCategory,
            thumb = it.strCategoryThumb,
            description = it.strCategoryDescription
        )
    }
}

fun ResponseCategory<CategoryModel>.asDatabaseModel() : List<CategoryEntity>{
    return categories.map {
        CategoryEntity(
            id = it.idCategory,
            idCategory = it.idCategory,
            strCategory = it.strCategory,
            strCategoryThumb = it.strCategoryThumb,
            strCategoryDescription = it.strCategoryDescription
        )
    }
}

fun ResponseCategory<CategoryModel>.asDomainModel() : List<com.shapeide.rasadesa.domain.Category>{
    return categories.map {
        com.shapeide.rasadesa.domain.Category(
            id = it.idCategory,
            name = it.strCategory,
            thumb = it.strCategoryThumb,
            description = it.strCategoryDescription
        )
    }
}