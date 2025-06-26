package com.dobrihlopez.financeassistant.feature.transaction.history.di

import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.impl.GetSortedExpenseTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.impl.GetSortedIncomeTransactionsUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @[Binds Named("usecaseSortedIncome")]
    abstract fun bindGetSortedIncomeTransaction(impl: GetSortedIncomeTransactionsUsecase): GetSortedTransactionsUsecase

    @[Binds Named("usecaseSortedExpense")]
    abstract fun bindGetSortedExpenseTransaction(impl: GetSortedExpenseTransactionsUsecase): GetSortedTransactionsUsecase
}
