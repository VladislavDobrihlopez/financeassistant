package com.dobrihlopez.financeassistant.feature.accounts

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.dobrihlopez.financeassistant.feature.accounts.data.network.AccountsApiService
import com.dobrihlopez.financeassistant.feature.accounts.data.repo.AccountsRepositoryImpl
import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import com.dobrihlopez.financeassistant.feature.accounts.domain.usecase.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.domain.usecase.UpdateAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountsModule {
    @Provides
    @Singleton
    fun provideAccountsApiService(retrofit: Retrofit): AccountsApiService =
        retrofit.create(AccountsApiService::class.java)

    @Provides
    @Singleton
    fun provideAccountsRepository(api: AccountsApiService): AccountsRepository =
        AccountsRepositoryImpl(api)

    @Provides
    fun provideGetFirstAccountUseCase(repository: AccountsRepository): GetFirstAccountUseCase =
        GetFirstAccountUseCase(repository)

    @Provides
    fun provideAccountsStoreFactory(
        storeFactory: StoreFactory,
        getFirstAccountUseCase: GetFirstAccountUseCase,
        updateAccountUseCase: UpdateAccountUseCase
    ): AccountsStore.AccountsStoreFactory =
        AccountsStore.AccountsStoreFactory(storeFactory, getFirstAccountUseCase, updateAccountUseCase)
} 