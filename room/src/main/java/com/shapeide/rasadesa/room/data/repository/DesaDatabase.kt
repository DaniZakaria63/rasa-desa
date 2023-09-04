package com.shapeide.rasadesa.room.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shapeide.rasadesa.room.data.source.AreaDao
import com.shapeide.rasadesa.room.domain.AreaEntity
import com.shapeide.rasadesa.room.data.source.CategoryDAO
import com.shapeide.rasadesa.room.domain.CategoryEntity
import com.shapeide.rasadesa.room.data.source.IngredientDAO
import com.shapeide.rasadesa.room.domain.IngredientEntity
import com.shapeide.rasadesa.room.domain.FilterMealEntity
import com.shapeide.rasadesa.room.data.source.FilterMealDAO
import com.shapeide.rasadesa.room.data.source.MealDAO
import com.shapeide.rasadesa.room.domain.MealEntity
import com.shapeide.rasadesa.room.data.source.MealSearchDAO
import com.shapeide.rasadesa.room.domain.MealSearchEntity

@Database(
    entities = [CategoryEntity::class, FilterMealEntity::class, AreaEntity::class, IngredientEntity::class, MealEntity::class, MealSearchEntity::class],
    version = 9,
    exportSchema = false
)
abstract class DesaDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDAO
    abstract val filterMealDao: FilterMealDAO
    abstract val areaDao: AreaDao
    abstract val ingredientDao: IngredientDAO
    abstract val mealDao: MealDAO
    abstract val searchDao: MealSearchDAO

    companion object{
        @JvmStatic
        val INSTANCE_NAME = "desa_database"
    }

    /**
     * functional of onCreate event inside Callback class would be populate the database
     * this time will be clear all database while new instance created

    private class RoomDBCallback() : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            categoryDao.deleteAll()
            filterMealDao.deleteAll_FM()
            areaDao.deleteAll()
            ingredientDao.deleteAll()
            mealDao.deleteAll()
            searchDao.deleteAll()
        }
    }
    */
}