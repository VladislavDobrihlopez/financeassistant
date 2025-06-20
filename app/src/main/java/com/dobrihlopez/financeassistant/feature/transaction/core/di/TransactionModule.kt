package com.dobrihlopez.financeassistant.feature.transaction.core.di

import com.dobrihlopez.financeassistant.core.network.ExpenseApi
import com.dobrihlopez.financeassistant.core.network.IncomeApi
import com.dobrihlopez.financeassistant.core.network.TransactionApi
import com.dobrihlopez.financeassistant.feature.transaction.expenses.data.ExpenseRepositoryImpl
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseRepository
import com.dobrihlopez.financeassistant.feature.transaction.income.data.IncomeRepositoryImpl
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionModule {
    @Binds
    @Singleton
    abstract fun bindIncomeRepo(impl: IncomeRepositoryImpl): IncomeRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepo(impl: ExpenseRepositoryImpl): ExpenseRepository

    companion object {
        @Provides
        fun provideTransactionApi(retrofit: Retrofit): TransactionApi =
            retrofit.create(TransactionApi::class.java)

        @Provides
        @Singleton
        fun provideIncomeApi(retrofit: Retrofit): IncomeApi = retrofit.create(IncomeApi::class.java)

        @Provides
        @Singleton
        fun provideExpenseApi(retrofit: Retrofit): ExpenseApi =
            retrofit.create(ExpenseApi::class.java)
    }
}
