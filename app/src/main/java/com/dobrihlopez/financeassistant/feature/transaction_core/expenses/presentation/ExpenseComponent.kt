package com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction_core.core.previewTransactions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface ExpenseComponent {
    val state: StateFlow<ExpenseStore.ExpenseScreenState>

    fun onHistoryClick()
    fun onFabClick()

    class DefaultExpenseComponent(
        val componentContext: ComponentContext,
        private val storeFactory: StoreFactory,
    ): ExpenseComponent, ComponentContext by componentContext {

        private fun restoreState() = stateKeeper.consume(STATE_KEY, strategy = ExpenseStore.ExpenseScreenState.serializer())

        private val initState = restoreState() ?: ExpenseStore.ExpenseScreenState.Succeeded(
            transactions = previewTransactions(),
            summaryText = "Всего",
            summaryValue = "436 558 ₽"
        )

        private val store = instanceKeeper.getStore {
            ExpenseStore.ExpenseStoreFactory(storeFactory).create(
                initialState = initState
            )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<ExpenseStore.ExpenseScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("expense_state", ExpenseStore.ExpenseScreenState.serializer()) {
                state.value
            }
        }

        override fun onHistoryClick() {
            store.accept(ExpenseStore.Intent.HistoryClick)
        }

        override fun onFabClick() {
            store.accept(ExpenseStore.Intent.AddExpense)
        }

        private companion object {
            const val STATE_KEY = "expense"
        }
    }
}
