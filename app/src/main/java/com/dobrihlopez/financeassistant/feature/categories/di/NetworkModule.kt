package com.dobrihlopez.financeassistant.feature.categories.di

import com.dobrihlopez.financeassistant.feature.categories.data.network.CategoryApi
import com.dobrihlopez.financeassistant.feature.categories.data.repo.CategoriesRepositoryImpl
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindCategoriesRepo(impl: CategoriesRepositoryImpl): CategoriesRepository

    companion object {
        @Provides
        @Singleton
        fun provideCategoriesApi(retrofit: Retrofit) = retrofit.create(CategoryApi::class.java)
    }
}