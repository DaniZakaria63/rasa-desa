package com.shapeide.rasadesa.hilt

import android.content.Context
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
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
    fun provideMealRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context) : MealRepository {
        return MealRepository(roomDB, apiEndpoint, context)
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint, context: Context): CategoryRepository {
        return CategoryRepository(roomDB, apiEndpoint, context)
    }
}