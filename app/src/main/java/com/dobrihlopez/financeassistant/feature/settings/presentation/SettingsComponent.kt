package com.dobrihlopez.financeassistant.feature.settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.settings.domain.AppSettingItem
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsStore.SettingsStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

interface SettingsComponent {
    val state: StateFlow<SettingsStore.SettingsScreenState>

    fun onSettingClick(setting: AppSettingItem)

    class DefaultSettingsComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val settingsStoreFactory: SettingsStoreFactory
    ) : SettingsComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = SettingsStore.SettingsScreenState.serializer())
            ?: SettingsStore.SettingsScreenState.Succeeded(
                items = AppSettingItem.all
            )

        private val store = instanceKeeper.getStore {
            settingsStoreFactory.create(initState)
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

        @AssistedFactory
        interface Factory {
            fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultSettingsComponent
        }

        private companion object {
            const val STATE_KEY = "settings"
        }
    }
} 