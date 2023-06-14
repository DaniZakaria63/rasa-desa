package com.shapeide.rasadesa.hilt

import android.content.Context
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.networks.APIEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context{
        return context
    }

    @Provides
    fun provideApiEndpoint(): APIEndpoint {
        return APIEndpoint.create()
    }

    @Provides
    fun provideRoomDB(@ApplicationContext context: Context) : RoomDB {
        val applicationScope = CoroutineScope(SupervisorJob())
        return RoomDB.getInstanceDB(context,applicationScope)
    }
}
