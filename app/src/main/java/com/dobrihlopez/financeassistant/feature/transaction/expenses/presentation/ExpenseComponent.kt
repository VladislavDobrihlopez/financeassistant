package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationComponent
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore
import com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation.ExpenseStore.ExpenseStoreFactory
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
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

    fun onNavigateBack()

    fun onRefreshList()

    sealed interface Child {
        data class Main(val component: ExpenseComponent) : Child

        data class History(val component: HistoryComponent) : Child

        data class TransactionCreator(val component: TransactionCreationComponent) : Child
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultExpenseComponent
    }

    class DefaultExpenseComponent
    @AssistedInject
    constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val expenseStoreFactory: ExpenseStoreFactory,
        private val historyComponentFactory: HistoryComponent.Factory,
        private val transactionComponentFactory: TransactionCreationComponent.Factory,
        @Named("usecaseSortedExpense") private val getSortedExpenseTransactionsUsecase: GetSortedTransactionsUsecase,
        @Named("usecaseCategoriesExpense") private val getExpenseCategoriesUsecase: GetTypedCategoriesUsecase
        ) : ExpenseComponent, ComponentContext by componentContext {
        private val stack = StackNavigation<Config>()

        override val childStack: Value<ChildStack<*, Child>> =
            childStack(
                source = stack,
                initialConfiguration = Config.Main,
                childFactory = ::child,
                key = "expense_stack",
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
                            isIncome = false,
                            getSortedTransactionsUsecase = getSortedExpenseTransactionsUsecase,
                            onTransactionSelected = { transaction ->
                                onExpenseClick(transaction)
                            }
                        ),
                    )

                is Config.TransactionCreator -> {
                    Child.TransactionCreator(
                        component = transactionComponentFactory.create(
                            componentContext = componentContext,
                            launchMode = if (config.transaction == null) {
                                TransactionCreationStore.LaunchMode.CREATING
                            } else {
                                TransactionCreationStore.LaunchMode.EDITING
                            },
                            transaction = config.transaction,
                            onFinish = {
                                onNavigateBack()
                                store.accept(ExpenseStore.Intent.LoadExpenses)
                            },
                            getTypedCategories = getExpenseCategoriesUsecase,
                        )
                    )
                }
            }

        private val initState =
            stateKeeper.consume(STATE_KEY, strategy = ExpenseStore.ExpenseScreenState.serializer())
                ?: ExpenseStore.ExpenseScreenState.Loading

        private val store =
            instanceKeeper.getStore {
                expenseStoreFactory.create(initState)
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<ExpenseStore.ExpenseScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register(STATE_KEY, ExpenseStore.ExpenseScreenState.serializer()) {
                state.value
            }

            lifecycle.doOnStart {
                if (state.value is ExpenseStore.ExpenseScreenState.Failed) {
                    onRefreshList()
                }
            }
        }

        override fun onRefreshList() {
            store.accept(ExpenseStore.Intent.LoadExpenses)
        }

        override fun onNavigateBack() {
            stack.pop()
        }

        override fun onHistoryClick() {
            stack.push(Config.History)
        }

        override fun onFabClick() {
            // store.accept(ExpenseStore.Intent.AddExpense)
            stack.push(
                Config.TransactionCreator(
                    isFromIncome = false,
                    transaction = null
                )
            )
        }

        override fun onExpenseClick(transaction: Transaction) {
            // store.accept(ExpenseStore.Intent.OnExpenseClick(transaction))
            stack.push(
                Config.TransactionCreator(
                    isFromIncome = false,
                    transaction = transaction
                )
            )
        }

        private companion object {
            const val STATE_KEY = "expense"
        }

        @Serializable
        private sealed class Config {
            @Serializable
            object Main : Config()

            @Serializable
            object History : Config()

            @Serializable
            data class TransactionCreator(
                val isFromIncome: Boolean,
                val transaction: Transaction? = null,
            ) : Config()
        }
    }
}
