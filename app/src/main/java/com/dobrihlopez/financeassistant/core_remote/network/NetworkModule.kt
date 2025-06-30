package com.dobrihlopez.financeassistant.core_remote.network

import android.content.Context
import android.net.ConnectivityManager
import com.dobrihlopez.financeassistant.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideConnectivityObserver(impl: ConnectivityObserver.DefaultConnectivityObserver): ConnectivityObserver = impl

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context,
    ): ConnectivityManager =
        try {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        } catch (e: Exception) {
            throw RuntimeException("Error getting Connectivity Manager")
        }

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://shmr-finance.ru/api/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        val authInterceptor =
            Interceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                        .build()
                chain.proceed(request)
            }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
        gson: Gson,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
}
