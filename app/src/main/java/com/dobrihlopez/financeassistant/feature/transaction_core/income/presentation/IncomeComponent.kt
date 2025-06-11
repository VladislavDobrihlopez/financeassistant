package com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction_core.core.previewIncomeTransactions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface IncomeComponent {
    val state: StateFlow<IncomeStore.IncomeScreenState>

    fun onHistoryClick()
    fun onFabClick()

    class DefaultIncomeComponent(
        val componentContext: ComponentContext,
        private val storeFactory: StoreFactory,
    ): IncomeComponent, ComponentContext by componentContext {

        private fun restoreState() = stateKeeper.consume(STATE_KEY, strategy = IncomeStore.IncomeScreenState.serializer())

        private val initState = restoreState() ?: IncomeStore.IncomeScreenState.Succeeded(
            transactions = previewIncomeTransactions(),
            summaryText = "Всего",
            summaryValue = "600 000 ₽"
        )

        private val store = instanceKeeper.getStore {
            IncomeStore.IncomeStoreFactory(storeFactory).create(
                initialState = initState
            )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<IncomeStore.IncomeScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("income_state", IncomeStore.IncomeScreenState.serializer()) {
                state.value
            }
        }

        override fun onHistoryClick() {
            store.accept(IncomeStore.Intent.HistoryClick)
        }

        override fun onFabClick() {
            store.accept(IncomeStore.Intent.AddIncome)
        }

        private companion object {
            const val STATE_KEY = "income"
        }
    }
}
