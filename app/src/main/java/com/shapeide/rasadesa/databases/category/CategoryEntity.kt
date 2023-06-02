package com.shapeide.rasadesa.databases.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domains.Category

@Entity(tableName = "tbl_category")
data class CategoryEntity constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "_id") val idCategory: Int,
    @ColumnInfo(name = "name") val strCategory: String,
    @ColumnInfo(name = "image_thumb_url") val strCategoryThumb: String,
    @ColumnInfo(name = "image_desc") val strCategoryDescription: String,
)

/* Map from table data type into domain data type */
fun List<CategoryEntity>.asDomainModel(): List<Category> {
    return map {
        Category(
            id = it.idCategory,
            name = it.strCategory,
            thumb = it.strCategoryThumb,
            description = it.strCategoryDescription
        )
    }
}