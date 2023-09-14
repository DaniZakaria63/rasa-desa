package com.shapeide.rasadesa.hilt

import android.content.Context
import androidx.room.Room
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.local.data.DesaDatabase
import com.shapeide.rasadesa.local.data.dao.RecipePreviewDao
import com.shapeide.rasadesa.local.data.repository.DefaultRecipeDataStore
import com.shapeide.rasadesa.local.data.source.RecipeDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideDesaDatabase(@ApplicationContext context: Context): DesaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DesaDatabase::class.java,
            DesaDatabase.INSTANCE_NAME
        )
            .fallbackToDestructiveMigration() // this function really scary
            .build()
    }

    @Provides
    fun provideRecipePreviewDao(desaDatabase: DesaDatabase): RecipePreviewDao =
        desaDatabase.recipePreviewDao()

    @Provides
    fun provideRecipeDataStore(
        recipePreviewDao: RecipePreviewDao,
        dispatcherProvider: DispatcherProvider
    ): RecipeDataStore =
        DefaultRecipeDataStore(recipePreviewDao, dispatcherProvider)
}