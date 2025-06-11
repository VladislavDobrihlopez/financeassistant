package com.dobrihlopez.financeassistant.feature.settings.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow


interface SettingsComponent {
    val state: StateFlow<SettingsScreenState>

    class DefaultAccountComponent(
        val componentContext: ComponentContext
    ): SettingsComponent, ComponentContext by componentContext {
        override val state: StateFlow<SettingsScreenState>
            get() = TODO("Not yet implemented")
    }
}
