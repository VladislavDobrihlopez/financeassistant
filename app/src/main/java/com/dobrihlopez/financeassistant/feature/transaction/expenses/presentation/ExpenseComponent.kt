package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core.previewTransactions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import com.dobrihlopez.financeassistant.core.domain.expenses.ExpenseRepository
import com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation.ExpenseStore.ExpenseStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

interface ExpenseComponent {
    val state: StateFlow<ExpenseStore.ExpenseScreenState>

    fun onHistoryClick()
    fun onFabClick()
    fun onExpenseClick(transaction: Transaction)

    class DefaultExpenseComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val expenseStoreFactory: ExpenseStoreFactory
    ) : ExpenseComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = ExpenseStore.ExpenseScreenState.serializer())
            ?: ExpenseStore.ExpenseScreenState.Loading

        private val store = instanceKeeper.getStore {
            expenseStoreFactory.create(initState)
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

        override fun onExpenseClick(transaction: Transaction) {
            store.accept(ExpenseStore.Intent.OnExpenseClick(transaction))
        }

        private companion object {
            const val STATE_KEY = "expense"
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultExpenseComponent
    }
}
