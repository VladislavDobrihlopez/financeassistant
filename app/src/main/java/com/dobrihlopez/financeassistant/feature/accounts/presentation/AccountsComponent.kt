package com.dobrihlopez.financeassistant.feature.accounts.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface AccountsComponent {
    val state: StateFlow<AccountScreenState>

    class DefaultAccountComponent(
        val componentContext: ComponentContext
    ): AccountsComponent, ComponentContext by componentContext {
        override val state: StateFlow<AccountScreenState>
            get() = TODO("Not yet implemented")
    }
}
