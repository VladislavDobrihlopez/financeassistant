package com.dobrihlopez.financeassistant.feature.settings.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem
import kotlinx.serialization.Serializable
import javax.inject.Inject

/**
 * Компонент SettingsStore.
 *
 * Ответственность: обрабатывать внешние события (Intent), внутренние события (Action)
 * через Executor и обновлять состояние экрана с помощью сообщений (Message) в Reducer.
 *
 * Взаимодействует с бизнес-логикой для получения и отображения списка пользовательских настроек.
 *
 * Реализован по архитектуре MVI с использованием библиотеки MVIKotlin.
 *
 * Поддерживает обработку кликов и загрузку настроек.
 */
interface SettingsStore : Store<SettingsStore.Intent, SettingsStore.SettingsScreenState, Nothing> {
    @Serializable
    sealed class SettingsScreenState {
        @Serializable
        data object Loading : SettingsScreenState()

        @Serializable
        data class Failed(
            @StringRes val errorResId: Int? = null,
        ) : SettingsScreenState()

        @Serializable
        data class Succeeded(
            val items: List<AppSettingItem>,
        ) : SettingsScreenState()
    }

    sealed class Intent {
        data object LoadSettings : Intent()

        data class SettingClick(val setting: AppSettingItem) : Intent()
    }

    class SettingsStoreFactory
        @Inject
        constructor(
            private val storeFactory: StoreFactory,
        ) {
            fun create(initialState: SettingsScreenState): SettingsStore = SettingsStoreImpl(storeFactory, initialState)

            private class SettingsStoreImpl(
                storeFactory: StoreFactory,
                initialState: SettingsScreenState,
            ) : SettingsStore,
                Store<Intent, SettingsScreenState, Nothing> by storeFactory.create(
                    name = "SettingsStore",
                    initialState = initialState,
                    executorFactory = { ExecutorImpl() },
                    reducer = ReducerImpl,
                )

            private class ExecutorImpl : CoroutineExecutor<Intent, Nothing, SettingsScreenState, Message, Nothing>() {
                override fun executeIntent(intent: Intent) {
                    when (intent) {
                        Intent.LoadSettings -> {
                            dispatch(Message.Succeeded(AppSettingItem.all))
                        }
                        is Intent.SettingClick -> {}
                    }
                }
            }

            private object ReducerImpl : Reducer<SettingsScreenState, Message> {
                override fun SettingsScreenState.reduce(msg: Message): SettingsScreenState {
                    return when (msg) {
                        is Message.Loading -> SettingsScreenState.Loading
                        is Message.Failed -> SettingsScreenState.Failed(msg.errorResId)
                        is Message.Succeeded -> SettingsScreenState.Succeeded(msg.items)
                    }
                }
            }

            private sealed class Message {
                data object Loading : Message()

                data class Failed(
                    @StringRes val errorResId: Int? = null,
                ) : Message()

                data class Succeeded(val items: List<AppSettingItem>) : Message()
            }
        }
}
