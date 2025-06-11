package com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation

import com.arkivanov.decompose.ComponentContext
import com.dobrihlopez.financeassistant.feature.transaction_core.core.TransactionScreenState
import kotlinx.coroutines.flow.StateFlow

interface IncomeComponent {
    val state: StateFlow<TransactionScreenState>

    class DefaultIncomeComponent(
        val componentContext: ComponentContext
    ): IncomeComponent, ComponentContext by componentContext {
        override val state: StateFlow<TransactionScreenState>
            get() = TODO("Not yet implemented")
    }
}
