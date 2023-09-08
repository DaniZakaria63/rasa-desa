package com.shapeide.rasadesa.hilt

import android.content.Context
import com.google.gson.GsonBuilder
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.remote.data.repository.DefaultNetworkRequest
import com.shapeide.rasadesa.remote.data.source.APIEndpoint
import com.shapeide.rasadesa.remote.data.source.NetworkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.InputStream
import java.util.Properties

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideApiConfigValue(@ApplicationContext context: Context): Pair<String, String>{
        val properties = Properties()
        return try {
            val rawResource = context.resources.openRawResource(R.raw.config)
            properties.load(rawResource)
            return Pair(
                properties.getProperty("api_app_id"),
                properties.getProperty("api_app_key")
            )
        }catch (e: Exception){
            Timber.e(e)
            Pair("-","-")
        }
    }

    @Provides
    fun provideApiEndpoint(apiConfigValue: Pair<String, String>): APIEndpoint {
        val interceptor = Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val authParam = request.url.newBuilder()
                .addQueryParameter("app_id", apiConfigValue.first)
                .addQueryParameter("app_key", apiConfigValue.second)
                .build()
            request = request.newBuilder().url(authParam).build()
            return@Interceptor chain.proceed(request)
        }

        val client = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(APIEndpoint.BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .build()
            .create(APIEndpoint::class.java)
    }

    @Provides
    fun provideNetworkRequest(apiEndpoint: APIEndpoint): NetworkRequest =
        DefaultNetworkRequest(apiEndpoint)
}