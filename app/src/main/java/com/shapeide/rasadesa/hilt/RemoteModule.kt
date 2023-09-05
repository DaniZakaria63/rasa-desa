package com.shapeide.rasadesa.hilt

import com.google.gson.GsonBuilder
import com.shapeide.rasadesa.remote.data.repository.DefaultNetworkRequest
import com.shapeide.rasadesa.remote.data.source.APIEndpoint
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideApiEndpoint(): APIEndpoint {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
            .baseUrl(APIEndpoint.BASE_URL)
            .build()
            .create(APIEndpoint::class.java)
    }

    @Provides
    fun provideNetworkRequest(apiEndpoint: APIEndpoint): NetworkRequest =
        DefaultNetworkRequest(apiEndpoint)
}