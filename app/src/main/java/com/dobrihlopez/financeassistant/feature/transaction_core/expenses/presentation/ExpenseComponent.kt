package com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation

import com.arkivanov.decompose.ComponentContext
import com.dobrihlopez.financeassistant.feature.transaction_core.core.TransactionScreenState
import kotlinx.coroutines.flow.StateFlow


interface ExpenseComponent {
    val state: StateFlow<TransactionScreenState>

    class DefaultExpenseComponent(
        val componentContext: ComponentContext
    ): ExpenseComponent, ComponentContext by componentContext {
        override val state: StateFlow<TransactionScreenState>
            get() = TODO("Not yet implemented")
    }
}
