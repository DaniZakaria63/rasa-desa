package com.shapeide.rasadesa.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_search")
data class MealSearchEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "id_ori") val id_ori: Int,
    @ColumnInfo(name = "search") val search: String,
    @ColumnInfo(name = "img_url") val img_url: String? = ""
)

fun List<MealSearchEntity>.asSearchDomain(): List<com.shapeide.rasadesa.core.domain.Search> {
    return map {
        com.shapeide.rasadesa.core.domain.Search(
            id = it.id_ori.toString(),
            text = it.search,
            is_local = true
        )
    }
}

fun com.shapeide.rasadesa.core.domain.Search.asDatabaseModel(): MealSearchEntity {
    return MealSearchEntity(
        id_ori = id.toInt(),
        search = text,
        img_url = ""
    )
}