package com.shapeide.rasadesa.hilt

import android.content.Context
import androidx.room.Room
import com.shapeide.rasadesa.room.data.repository.DesaDatabase
import com.shapeide.rasadesa.data.source.APIEndpoint
import com.shapeide.rasadesa.coroutines.DefaultDispatcherProvider
import com.shapeide.rasadesa.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiEndpoint(): APIEndpoint {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(APIEndpoint.BASE_URL)
            .build()
            .create(APIEndpoint::class.java)
    }

    @Provides
    fun provideDesaDatabase(@ApplicationContext context: Context) : DesaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DesaDatabase::class.java,
            DesaDatabase.INSTANCE_NAME
        )
            .fallbackToDestructiveMigration() // this function really scary
            .build()
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
