package com.shapeide.rasadesa.hilt

import android.content.Context
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.databases.area.AreaRepository
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.ingredient.IngredientRepository
import com.shapeide.rasadesa.databases.filtermeal.FilterMealRepository
import com.shapeide.rasadesa.databases.meal.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.ui.search.SearchRoomManager
import com.shapeide.rasadesa.utills.DispatcherProvider
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
    fun provideFilterMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : FilterMealRepository {
        return FilterMealRepository(desaDatabase, apiEndpoint, dispatcherProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider): CategoryRepository {
        return CategoryRepository(desaDatabase, apiEndpoint, dispatcherProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideAreaRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : AreaRepository {
        return AreaRepository(desaDatabase, apiEndpoint, dispatcherProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideIngredientRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : IngredientRepository {
        return IngredientRepository(desaDatabase, apiEndpoint, dispatcherProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : MealRepository {
        return MealRepository(desaDatabase, apiEndpoint, dispatcherProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRoomManager(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : SearchRoomManager {
        return SearchRoomManager(desaDatabase, apiEndpoint, dispatcherProvider)
    }
}