package com.shapeide.rasadesa.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shapeide.rasadesa.databases.DesaDatabase
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.utills.DefaultDispatcherProvider
import com.shapeide.rasadesa.utills.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
