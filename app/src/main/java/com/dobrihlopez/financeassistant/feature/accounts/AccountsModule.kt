package com.dobrihlopez.financeassistant.feature.accounts

import com.dobrihlopez.financeassistant.feature.accounts.data.AccountsApiService
import com.dobrihlopez.financeassistant.feature.accounts.data.AccountsRepository
import com.dobrihlopez.financeassistant.feature.accounts.data.AccountsRepositoryImpl
import com.dobrihlopez.financeassistant.feature.accounts.domain.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.domain.UpdateAccountUseCase
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
    fun provideUpdateAccountUseCase(repository: AccountsRepository): UpdateAccountUseCase =
        UpdateAccountUseCase(repository)

    @Provides
    fun provideAccountsStoreFactory(
        storeFactory: com.arkivanov.mvikotlin.core.store.StoreFactory,
        getFirstAccountUseCase: GetFirstAccountUseCase,
        updateAccountUseCase: UpdateAccountUseCase
    ): AccountsStore.AccountsStoreFactory =
        AccountsStore.AccountsStoreFactory(storeFactory, getFirstAccountUseCase, updateAccountUseCase)
} 