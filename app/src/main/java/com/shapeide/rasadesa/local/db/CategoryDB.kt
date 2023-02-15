package com.shapeide.rasadesa.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shapeide.rasadesa.local.dao.CategoryDAO
import com.shapeide.rasadesa.local.entity.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoryDB : RoomDatabase(){
    abstract fun categoryDao(): CategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: CategoryDB? = null
        private val INSTANCE_NAME = "category_db"

        fun getInstanceDB(context: Context): CategoryDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryDB::class.java,
                    INSTANCE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}