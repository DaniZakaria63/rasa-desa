package com.shapeide.rasadesa.databases.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shapeide.rasadesa.domains.Search

@Entity(tableName = "tbl_search")
data class MealSearchEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "id_ori") val id_ori: Int,
    @ColumnInfo(name = "search") val search: String,
    @ColumnInfo(name = "img_url") val img_url: String? = ""
)

fun List<MealSearchEntity>.asSearchDomain(): List<Search> {
    return map {
        Search(id = it.id_ori.toString(), text = it.search, is_local = true)
    }
}

fun Search.asDatabaseModel(): MealSearchEntity {
    return MealSearchEntity(
        id_ori = id.toInt(),
        search = text,
        img_url = ""
    )
}