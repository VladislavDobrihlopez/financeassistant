package com.dobrihlopez.financeassistant.feature.accounts.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore.AccountsStoreFactory
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.Currency
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
    fun onCurrencySelected(account: UserAccountDetailed, currency: Currency)
    fun onBalanceChanged(account: UserAccountDetailed, newBalance: String)

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultAccountComponent
    }

    class DefaultAccountComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val accountsStoreFactory: AccountsStoreFactory
    ) : AccountsComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = AccountsStore.AccountScreenState.serializer())
            ?: AccountsStore.AccountScreenState.Loading

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

            lifecycle.doOnResume {
                if (state.value is AccountsStore.AccountScreenState.Failed) {
                    store.accept(AccountsStore.Intent.RefreshAccount)
                }
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

        override fun onCurrencySelected(account: UserAccountDetailed, currency: Currency) {
            val currencyCode = when (currency) {
                Currency.Ruble -> "RUB"
                Currency.Usd -> "USD"
                Currency.Euro -> "EUR"
            }
            store.accept(
                AccountsStore.Intent.UpdateAccount(
                    id = account.id,
                    name = account.name,
                    balance = account.balance,
                    currency = currencyCode
                )
            )
        }

        override fun onBalanceChanged(account: UserAccountDetailed, newBalance: String) {
            store.accept(
                AccountsStore.Intent.UpdateAccount(
                    id = account.id,
                    name = account.name,
                    balance = newBalance,
                    currency = account.currency
                )
            )
        }

        private companion object {
            const val STATE_KEY = "accounts"
        }
    }
}
