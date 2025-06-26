package com.dobrihlopez.financeassistant.feature.transaction.di

import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionApi
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.network.TransactionCrudApi
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.repository.TransactionRepositoryImpl
import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionModule {
    @Binds
    abstract fun bindRepository(impl: TransactionRepositoryImpl): TransactionRepository

    companion object {
        @Provides
        fun provideTransactionCrudApi(retrofit: Retrofit): TransactionCrudApi =
            retrofit.create(TransactionCrudApi::class.java)

        @Provides
        fun provideTransactionApi(retrofit: Retrofit): TransactionApi =
            retrofit.create(TransactionApi::class.java)
    }
}
