package com.shapeide.rasadesa.local.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shapeide.rasadesa.local.data.dao.RecipePreviewDao
import com.shapeide.rasadesa.local.domain.RecipePreviewEntity
import com.shapeide.rasadesa.local.typeconverter.ListDietConverter

@TypeConverters(ListDietConverter::class)
@Database(
    entities = [RecipePreviewEntity::class],
    version = 10,
    exportSchema = false
)
abstract class DesaDatabase : RoomDatabase() {
    abstract fun recipePreviewDao(): RecipePreviewDao

    companion object{
        @JvmStatic
        val INSTANCE_NAME = "desa_database"
    }
}