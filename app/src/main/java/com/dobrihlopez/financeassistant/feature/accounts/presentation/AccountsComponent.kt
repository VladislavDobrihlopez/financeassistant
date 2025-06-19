package com.dobrihlopez.financeassistant.feature.accounts.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore.AccountsStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface AccountsComponent {
    val state: StateFlow<AccountsStore.AccountScreenState>

    fun onEditClick()
    fun onFabClick()
    fun onBalanceClick()
    fun onCurrencyClick()

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultAccountComponent
    }

    class DefaultAccountComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val accountsStoreFactory: AccountsStoreFactory
    ) : AccountsComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = AccountsStore.AccountScreenState.serializer())
            ?: AccountsStore.AccountScreenState.Succeeded(
                account = provideAccount()
            )

        private val store = instanceKeeper.getStore {
            accountsStoreFactory.create(initState)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<AccountsStore.AccountScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("accounts_state", AccountsStore.AccountScreenState.serializer()) {
                state.value
            }
        }

        override fun onEditClick() {
            store.accept(AccountsStore.Intent.EditAccount)
        }

        override fun onFabClick() {
            store.accept(AccountsStore.Intent.AddAccount)
        }

        override fun onBalanceClick() {
            store.accept(AccountsStore.Intent.BalanceClick)
        }

        override fun onCurrencyClick() {
            store.accept(AccountsStore.Intent.CurrencyClick)
        }

        private companion object {
            const val STATE_KEY = "accounts"
        }
    }
}

private fun provideAccount() = UserAccountDetailed(
    id = 1,
    name = "Мой счёт",
    balance = "-670 000",
    currency = "₽",
    createdAt = "",
    updatedAt = "",
    expenseStats = emptyList(),
    incomeStatistics = emptyList()
)
