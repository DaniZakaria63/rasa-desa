package com.shapeide.rasadesa.hilt

import com.shapeide.rasadesa.room.data.repository.DesaDatabase
import com.shapeide.rasadesa.data.repository.FilterMealRepository
import com.shapeide.rasadesa.data.source.APIEndpoint
import com.shapeide.rasadesa.data.repository.SearchRoomManager
import com.shapeide.rasadesa.coroutines.DispatcherProvider
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
    fun provideFilterMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : com.shapeide.rasadesa.data.repository.FilterMealRepository {
        return com.shapeide.rasadesa.data.repository.FilterMealRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider): com.shapeide.rasadesa.data.repository.CategoryRepository {
        return com.shapeide.rasadesa.data.repository.CategoryRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAreaRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : com.shapeide.rasadesa.data.repository.DefaultAreaRepository {
        return com.shapeide.rasadesa.data.repository.DefaultAreaRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideIngredientRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : com.shapeide.rasadesa.data.repository.IngredientRepository {
        return com.shapeide.rasadesa.data.repository.IngredientRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : com.shapeide.rasadesa.data.repository.MealRepository {
        return com.shapeide.rasadesa.data.repository.MealRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRoomManager(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: DispatcherProvider) : SearchRoomManager {
        return SearchRoomManager(desaDatabase, apiEndpoint, dispatcherProvider)
    }
}