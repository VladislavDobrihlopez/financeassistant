package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import kotlinx.serialization.Serializable
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.dobrihlopez.financeassistant.feature.accounts.domain.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.accounts.domain.UpdateAccountUseCase

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
        data class UpdateAccount(val id: Int, val name: String, val balance: String, val currency: String): Intent()
        data object RefreshAccount: Intent()
    }

    class AccountsStoreFactory @Inject constructor(
        private val storeFactory: StoreFactory,
        private val getFirstAccountUseCase: GetFirstAccountUseCase,
        private val updateAccountUseCase: UpdateAccountUseCase
    ) {
        fun create(initialState: AccountsStore.AccountScreenState): AccountsStore =
            AccountsStoreImpl(storeFactory, initialState, getFirstAccountUseCase, updateAccountUseCase)

        private class AccountsStoreImpl(
            storeFactory: StoreFactory,
            initialState: AccountScreenState,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val updateAccountUseCase: UpdateAccountUseCase
        ) : AccountsStore, Store<Intent, AccountScreenState, Nothing> by storeFactory.create(
            name = "AccountsStore",
            initialState = initialState,
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(getFirstAccountUseCase, updateAccountUseCase) },
            reducer = ReducerImpl
        )

        private class BootstrapperImpl(): CoroutineBootstrapper<Action>() {
            override fun invoke() {
                dispatch(Action.LoadData)
            }
        }

        private class ExecutorImpl(
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val updateAccountUseCase: UpdateAccountUseCase
        ): CoroutineExecutor<Intent, Action, AccountScreenState, Message, Nothing>() {
            override fun executeAction(action: Action) {
                super.executeAction(action)
                when (action) {
                    Action.LoadData -> {
                        executeIntent(Intent.LoadAccount)
                    }
                }
            }

            override fun executeIntent(intent: Intent) {
                when (intent) {
                    Intent.LoadAccount -> {
                        scope.launch {
                            try {
                                val account = getFirstAccountUseCase()
                                if (account != null) {
                                    dispatch(Message.Succeeded(account))
                                } else {
                                    dispatch(Message.Failed())
                                }
                            } catch (e: Exception) {
                                dispatch(Message.Failed())
                            }
                        }
                    }
                    is Intent.UpdateAccount -> {
                        dispatch(Message.Loading)
                        scope.launch {
                            try {
                                val updated = updateAccountUseCase(
                                    intent.id,
                                    intent.name,
                                    intent.balance,
                                    intent.currency
                                )
                                dispatch(Message.Updated(updated))
                            } catch (e: Exception) {
                                dispatch(Message.Failed())
                            }
                        }
                    }
                    Intent.EditAccount -> {}
                    Intent.AddAccount -> {}
                    Intent.BalanceClick -> {}
                    Intent.CurrencyClick -> {}
                    Intent.RefreshAccount -> {}
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
                    is Message.Updated -> AccountScreenState.Succeeded(msg.account)
                }
            }
        }

        private sealed class Action {
            data object LoadData: Action()
        }

        private sealed class Message {
            data object Loading: Message()
            data class Failed(@StringRes val errorResId: Int? = null): Message()
            data class Succeeded(val account: UserAccountDetailed): Message()
            data class Updated(val account: UserAccountDetailed): Message()
        }
    }
} 