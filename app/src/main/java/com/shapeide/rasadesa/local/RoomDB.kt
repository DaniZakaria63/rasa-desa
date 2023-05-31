package com.shapeide.rasadesa.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shapeide.rasadesa.local.dao.CategoryDAO
import com.shapeide.rasadesa.local.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract val categoryDao: CategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null
        private val INSTANCE_NAME = "desa_database"

        fun getInstanceDB(context: Context, scope: CoroutineScope): RoomDB {
            return INSTANCE ?: synchronized(RoomDB::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    INSTANCE_NAME
                ).addCallback(RoomDBCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class RoomDBCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.categoryDao)
                }
            }
        }

        suspend fun populateDatabase(categoryDAO: CategoryDAO) {
            scope.launch {
                // delete all content
                categoryDAO.deleteAll()

                /* TODO: ADD SOME CODES FOR INITIAL DATABASE */
            }
        }

    }
}