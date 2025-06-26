package com.dobrihlopez.financeassistant.feature.accounts.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.dobrihlopez.financeassistant.feature.accounts.data.network.AccountsApiService
import com.dobrihlopez.financeassistant.feature.accounts.data.repository.AccountsRepositoryImpl
import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import com.dobrihlopez.financeassistant.core.usecase.account.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.domain.usecase.UpdateAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountsModule {
    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountsRepositoryImpl): AccountsRepository

    companion object {
        @Provides
        @Singleton
        fun provideAccountsApiService(retrofit: Retrofit): AccountsApiService = retrofit.create(AccountsApiService::class.java)

        @Provides
        fun provideAccountsStoreFactory(
            storeFactory: StoreFactory,
            getFirstAccountUseCase: GetFirstAccountUseCase,
            updateAccountUseCase: UpdateAccountUseCase,
        ): AccountsStore.AccountsStoreFactory =
            AccountsStore.AccountsStoreFactory(
                storeFactory,
                getFirstAccountUseCase,
                updateAccountUseCase,
            )
    }
}
