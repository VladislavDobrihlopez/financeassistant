package com.dobrihlopez.financeassistant.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseStore
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeStore
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {
    @Provides
    @Singleton
    fun provideStoreFactory(): StoreFactory =
        LoggingStoreFactory(DefaultStoreFactory())
} 