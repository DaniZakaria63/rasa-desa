package com.shapeide.rasadesa.hilt

import android.content.Context
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.area.AreaRepository
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.ingredient.IngredientRepository
import com.shapeide.rasadesa.databases.filtermeal.FilterMealRepository
import com.shapeide.rasadesa.databases.meal.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.ui.search.SearchRoomManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule{

    @Provides
    @ViewModelScoped
    fun provideFilterMealRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : FilterMealRepository {
        return FilterMealRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context): CategoryRepository {
        return CategoryRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideAreaRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : AreaRepository {
        return AreaRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideIngredientRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : IngredientRepository {
        return IngredientRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideMealRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : MealRepository {
        return MealRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRoomManager(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : SearchRoomManager {
        return SearchRoomManager(roomDB, apiEndpoint, context)
    }
}