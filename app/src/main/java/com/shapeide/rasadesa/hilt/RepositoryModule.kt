package com.shapeide.rasadesa.hilt

import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule{

    @Provides
    @ViewModelScoped
    fun provideMealRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint) : MealRepository {
        return MealRepository(roomDB, apiEndpoint)
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(roomDB: RoomDB, apiEndpoint: APIEndpoint): CategoryRepository {
        return CategoryRepository(roomDB, apiEndpoint)
    }

}