package com.dobrihlopez.financeassistant.feature.settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsStore.SettingsStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

/**
 * Компонент экрана настроек.
 *
 * Предоставляет текущее состояние экрана и обрабатывает пользовательские действия,
 * такие как нажатие на элемент списка настроек.
 *
 * Отвечает за передачу пользовательских событий в хранилище (SettingsStore)
 * и предоставляет состояние UI.
 *
 * Содержит работу с ЖЦ через LifecycleOwner, работу со store через InstanceKeeperOwner,
 * работу со стейтом экрана через StateKeeperOwner.
 *
 * Используется в архитектуре Decompose.
 *
 * @see ComponentContext
 */
interface SettingsComponent {
    val state: StateFlow<SettingsStore.SettingsScreenState>

    fun onSettingClick(setting: AppSettingItem)

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultSettingsComponent
    }

    class DefaultSettingsComponent
        @AssistedInject
        constructor(
            @Assisted("componentContext") private val componentContext: ComponentContext,
            private val settingsStoreFactory: SettingsStoreFactory,
        ) : SettingsComponent, ComponentContext by componentContext {
            private val initState =
                stateKeeper.consume(STATE_KEY, SettingsStore.SettingsScreenState.serializer())
                    ?: SettingsStore.SettingsScreenState.Succeeded(
                        items = AppSettingItem.all,
                    )

            private val store =
                instanceKeeper.getStore {
                    settingsStoreFactory.create(initState)
                }

            @OptIn(ExperimentalCoroutinesApi::class)
            override val state: StateFlow<SettingsStore.SettingsScreenState>
                get() = store.stateFlow

            init {
                stateKeeper.register(STATE_KEY, SettingsStore.SettingsScreenState.serializer()) {
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
