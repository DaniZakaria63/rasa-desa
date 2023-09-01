package com.shapeide.rasadesa.databases

import android.content.Context
import androidx.room.Database
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
abstract class DesaDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDAO
    abstract val filterMealDao: FilterMealDAO
    abstract val areaDao: AreaDAO
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