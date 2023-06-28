package com.shapeide.rasadesa.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shapeide.rasadesa.databases.area.AreaDAO
import com.shapeide.rasadesa.databases.area.AreaEntity
import com.shapeide.rasadesa.databases.category.CategoryDAO
import com.shapeide.rasadesa.databases.category.CategoryEntity
import com.shapeide.rasadesa.databases.ingredient.IngredientDAO
import com.shapeide.rasadesa.databases.ingredient.IngredientEntity
import com.shapeide.rasadesa.databases.filtermeal.FilterMealEntity
import com.shapeide.rasadesa.databases.filtermeal.FilterMealDAO
import com.shapeide.rasadesa.databases.meal.MealDAO
import com.shapeide.rasadesa.databases.meal.MealEntity
import com.shapeide.rasadesa.databases.search.MealSearchDAO
import com.shapeide.rasadesa.databases.search.MealSearchEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CategoryEntity::class, FilterMealEntity::class, AreaEntity::class, IngredientEntity::class, MealEntity::class, MealSearchEntity::class],
    version = 9,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract val categoryDao: CategoryDAO
    abstract val filterMealDao: FilterMealDAO
    abstract val areaDao: AreaDAO
    abstract val ingredientDao: IngredientDAO
    abstract val mealDao: MealDAO
    abstract val searchDao: MealSearchDAO

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
                )
                    .addCallback(RoomDBCallback(scope))
                    .fallbackToDestructiveMigration() // this function really scary
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    /**
     * functional of onCreate event inside Callback class would be populate the database
     * this time will be clear all database while new instance created
     */
    private class RoomDBCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.categoryDao.deleteAll()
                    database.filterMealDao.deleteAll_FM()
                    database.areaDao.deleteAll()
                    database.ingredientDao.deleteAll()
                    database.mealDao.deleteAll()
                    database.searchDao.deleteAll()
                }
            }
        }
    }
}