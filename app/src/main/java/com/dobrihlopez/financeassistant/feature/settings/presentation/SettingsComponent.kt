package com.dobrihlopez.financeassistant.feature.settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.settings.domain.AppSettingItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {
    val state: StateFlow<SettingsStore.SettingsScreenState>

    fun onSettingClick(setting: AppSettingItem)

    class DefaultAccountComponent(
        val componentContext: ComponentContext,
        private val storeFactory: StoreFactory,
    ): SettingsComponent, ComponentContext by componentContext {

        private fun restoreState() = stateKeeper.consume(STATE_KEY, strategy = SettingsStore.SettingsScreenState.serializer())

        private val initState = restoreState() ?: SettingsStore.SettingsScreenState.Succeeded(
            items = AppSettingItem.all
        )

        private val store = instanceKeeper.getStore {
            SettingsStore.SettingsStoreFactory(storeFactory).create(
                initialState = initState
            )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<SettingsStore.SettingsScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("settings_state", SettingsStore.SettingsScreenState.serializer()) {
                state.value
            }
        }

        override fun onSettingClick(setting: AppSettingItem) {
            store.accept(SettingsStore.Intent.SettingClick(setting))
        }

        private companion object {
            const val STATE_KEY = "settings"
        }
    }
} 