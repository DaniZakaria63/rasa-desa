package com.shapeide.rasadesa.hilt

import com.shapeide.rasadesa.room.data.repository.DesaDatabase
import com.shapeide.rasadesa.data.repository.FilterMealRepository
import com.shapeide.rasadesa.remote.data.source.APIEndpoint
import com.shapeide.rasadesa.remote.data.repository.SearchRoomManager
import com.shapeide.rasadesa.core.coroutines.DispatcherProvider
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
    fun provideFilterMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider) : com.shapeide.rasadesa.data.repository.FilterMealRepository {
        return com.shapeide.rasadesa.data.repository.FilterMealRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider): com.shapeide.rasadesa.core.data.repository.CategoryRepository {
        return com.shapeide.rasadesa.core.data.repository.CategoryRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAreaRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider) : com.shapeide.rasadesa.core.data.repository.DefaultAreaRepository {
        return com.shapeide.rasadesa.core.data.repository.DefaultAreaRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideIngredientRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider) : com.shapeide.rasadesa.core.data.repository.IngredientRepository {
        return com.shapeide.rasadesa.core.data.repository.IngredientRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideMealRepository(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider) : com.shapeide.rasadesa.core.data.repository.MealRepository {
        return com.shapeide.rasadesa.core.data.repository.MealRepository(
            desaDatabase,
            apiEndpoint,
            dispatcherProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRoomManager(desaDatabase: DesaDatabase, apiEndpoint: APIEndpoint, dispatcherProvider: com.shapeide.rasadesa.core.coroutines.DispatcherProvider) : SearchRoomManager {
        return SearchRoomManager(desaDatabase, apiEndpoint, dispatcherProvider)
    }
}