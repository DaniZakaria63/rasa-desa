package com.shapeide.rasadesa.hilt

import com.shapeide.rasadesa.domain.coroutines.DefaultDispatcherProvider
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.core.data.repository.DefaultRecipeRepository
import com.shapeide.rasadesa.core.data.source.RecipeRepository
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import com.shapeide.rasadesa.local.data.source.RecipeDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideRecipeRepository(
        recipeDataStore: RecipeDataStore,
        networkRequest: NetworkRequest,
        dispatcherProvider: DispatcherProvider
    ): RecipeRepository =
        DefaultRecipeRepository(recipeDataStore, networkRequest, dispatcherProvider)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()
}