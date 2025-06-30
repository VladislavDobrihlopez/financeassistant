package com.dobrihlopez.financeassistant.di

import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.core.usecase.category.impl.GetExpenseCategoriesUsecase
import com.dobrihlopez.financeassistant.core.usecase.category.impl.GetIncomeCategoriesUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @[Binds Named("usecaseCategoriesExpense")]
    abstract fun bindGetExpenseCategoriesTransaction(impl: GetExpenseCategoriesUsecase): GetTypedCategoriesUsecase

    @[Binds Named("usecaseCategoriesIncome")]
    abstract fun bindGetIncomeCategoriesTransaction(impl: GetIncomeCategoriesUsecase): GetTypedCategoriesUsecase
}
