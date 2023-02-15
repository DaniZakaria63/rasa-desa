package com.shapeide.rasadesa.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val image_url : String,
    val image_location : String,
    val image_name : String
)