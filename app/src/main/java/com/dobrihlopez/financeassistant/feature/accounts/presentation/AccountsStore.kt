package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import kotlinx.serialization.Serializable

interface AccountsStore: Store<AccountsStore.Intent, AccountsStore.AccountScreenState, Nothing> {
    @Serializable
    sealed class AccountScreenState {
        @Serializable
        data object Loading: AccountScreenState()
        @Serializable
        data class Failed(@StringRes val errorResId: Int? = null): AccountScreenState()
        @Serializable
        data class Succeeded(
            val account: UserAccountDetailed
        ): AccountScreenState()
    }

    sealed class Intent {
        data object LoadAccount: Intent()
        data object EditAccount: Intent()
        data object AddAccount: Intent()
        data object BalanceClick: Intent()
        data object CurrencyClick: Intent()
    }

    class AccountsStoreFactory(
        private val storeFactory: StoreFactory
    ) {
        fun create(initialState: AccountScreenState): AccountsStore =
            object :
                AccountsStore,
                Store<Intent, AccountScreenState, Nothing> by storeFactory.create(
                    name = "AccountsStore",
                    initialState = initialState,
                    executorFactory = { ExecutorImpl() },
                    reducer = ReducerImpl
                ) {}

        private class ExecutorImpl: CoroutineExecutor<Intent, Nothing, AccountScreenState, Message, Nothing>() {
            override fun executeIntent(intent: Intent) {
                when (intent) {
                    Intent.LoadAccount -> {}
                    Intent.EditAccount -> {}
                    Intent.AddAccount -> {}
                    Intent.BalanceClick -> {}
                    Intent.CurrencyClick -> {}
                }
            }
        }

        private object ReducerImpl: Reducer<AccountScreenState, Message> {
            override fun AccountScreenState.reduce(
                msg: Message,
            ): AccountScreenState {
                return when (msg) {
                    is Message.Loading -> AccountScreenState.Loading
                    is Message.Failed -> AccountScreenState.Failed(msg.errorResId)
                    is Message.Succeeded -> AccountScreenState.Succeeded(msg.account)
                }
            }
        }

        sealed class Message {
            data object Loading: Message()
            data class Failed(@StringRes val errorResId: Int? = null): Message()
            data class Succeeded(val account: UserAccountDetailed): Message()
        }
    }
} 