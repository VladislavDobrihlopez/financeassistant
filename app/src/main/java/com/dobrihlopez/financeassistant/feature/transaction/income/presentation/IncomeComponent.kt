package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryComponent
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeStore.IncomeStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import javax.inject.Named

interface IncomeComponent {
    val childStack: Value<ChildStack<*, Child>>
    val state: StateFlow<IncomeStore.IncomeScreenState>

    fun onHistoryClick()

    fun onFabClick()

    sealed interface Child {
        data class Main(val component: IncomeComponent) : Child

        data class History(val component: HistoryComponent) : Child
    }

    class DefaultIncomeComponent
        @AssistedInject
        constructor(
            @Assisted("componentContext") private val componentContext: ComponentContext,
            private val incomeStoreFactory: IncomeStoreFactory,
            private val historyComponentFactory: HistoryComponent.Factory,
            @Named("usecaseIncome") private val getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
        ) : IncomeComponent, ComponentContext by componentContext {
            private val stack = StackNavigation<Config>()

            override val childStack: Value<ChildStack<Config, Child>> =
                childStack(
                    source = stack,
                    initialConfiguration = Config.Main,
                    childFactory = ::child,
                    key = "income_stack",
                    handleBackButton = true,
                    serializer = Config.serializer(),
                )

            private fun child(
                config: Config,
                componentContext: ComponentContext,
            ): Child =
                when (config) {
                    Config.Main -> Child.Main(this)
                    Config.History ->
                        Child.History(
                            historyComponentFactory.create(
                                componentContext,
                                isIncome = true,
                                getSortedTransactionsUsecase,
                            ),
                        )
                }

            private val initState =
                stateKeeper.consume(STATE_KEY, strategy = IncomeStore.IncomeScreenState.serializer())
                    ?: IncomeStore.IncomeScreenState.Loading

            private val store =
                instanceKeeper.getStore {
                    incomeStoreFactory.create(initState)
                }

            @OptIn(ExperimentalCoroutinesApi::class)
            override val state: StateFlow<IncomeStore.IncomeScreenState>
                get() = store.stateFlow

            init {
                stateKeeper.register("income_state", IncomeStore.IncomeScreenState.serializer()) {
                    state.value
                }

                lifecycle.doOnResume {
                    if (state.value is IncomeStore.IncomeScreenState.Failed) {
                        store.accept(IncomeStore.Intent.LoadIncome)
                    }
                }
            }

            override fun onHistoryClick() {
                stack.push(Config.History)
            }

            override fun onFabClick() {
                store.accept(IncomeStore.Intent.AddIncome)
            }

            private companion object {
                const val STATE_KEY = "income"
            }

            @Serializable
            sealed class Config {
                @Serializable
                object Main : Config()

                @Serializable
                object History : Config()
            }
        }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultIncomeComponent
    }
}
