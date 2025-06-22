package com.dobrihlopez.financeassistant.feature.transaction.di

import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionModule {
    companion object {
        @Provides
        fun provideTransactionApi(retrofit: Retrofit): TransactionApi = retrofit.create(TransactionApi::class.java)
    }
}
