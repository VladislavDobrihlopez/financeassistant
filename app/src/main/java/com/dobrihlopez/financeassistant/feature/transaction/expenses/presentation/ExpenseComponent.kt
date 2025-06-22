package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation.ExpenseStore.ExpenseStoreFactory
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.impl.GetSortedExpenseTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import javax.inject.Named

interface ExpenseComponent {
    val childStack: Value<ChildStack<*, Child>>
    val state: StateFlow<ExpenseStore.ExpenseScreenState>
    fun onHistoryClick()
    fun onFabClick()
    fun onExpenseClick(transaction: Transaction)

    sealed interface Child {
        data class Main(val component: ExpenseComponent) : Child
        data class History(val component: HistoryComponent) : Child
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultExpenseComponent
    }

    class DefaultExpenseComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val expenseStoreFactory: ExpenseStoreFactory,
        private val historyComponentFactory: HistoryComponent.Factory,
        @Named("usecaseExpense") private val getSortedExpenseTransactionsUsecase: GetSortedTransactionsUsecase,
    ) : ExpenseComponent, ComponentContext by componentContext {

        private val stack = StackNavigation<Config>()

        override val childStack: Value<ChildStack<Config, Child>> = childStack(
            source = stack,
            initialConfiguration = Config.Main,
            childFactory = ::child,
            key = "expense_stack",
            handleBackButton = true,
            serializer = Config.serializer()
        )

        private fun child(config: Config, componentContext: ComponentContext): Child =
            when (config) {
                Config.Main -> Child.Main(this)
                Config.History -> Child.History(
                    historyComponentFactory.create(
                        componentContext,
                        isIncome = false,
                        getSortedExpenseTransactionsUsecase
                    )
                )
            }

        private val initState =
            stateKeeper.consume(STATE_KEY, strategy = ExpenseStore.ExpenseScreenState.serializer())
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

            lifecycle.doOnResume {
                if (state.value is ExpenseStore.ExpenseScreenState.Failed) {
                    store.accept(ExpenseStore.Intent.LoadExpenses)
                }
            }
        }

        override fun onHistoryClick() {
            stack.push(Config.History)
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

        @Serializable
        sealed class Config {
            @Serializable
            object Main : Config()

            @Serializable
            object History : Config()
        }
    }
}
