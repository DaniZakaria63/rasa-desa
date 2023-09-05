package com.shapeide.rasadesa.room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shapeide.rasadesa.room.data.dao.RecipePreviewDao
import com.shapeide.rasadesa.room.domain.RecipePreviewEntity
import com.shapeide.rasadesa.room.typeconverter.ListDietConverter

@TypeConverters(ListDietConverter::class)
@Database(
    entities = [RecipePreviewEntity::class],
    version = 9,
    exportSchema = false
)
abstract class DesaDatabase : RoomDatabase() {
    abstract fun recipePreviewDao(): RecipePreviewDao

    companion object{
        @JvmStatic
        val INSTANCE_NAME = "desa_database"
    }
}