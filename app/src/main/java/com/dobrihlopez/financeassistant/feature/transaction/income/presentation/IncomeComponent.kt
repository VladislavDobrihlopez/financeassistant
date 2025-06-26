package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationComponent
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryComponent
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeComponent.Child.History
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeComponent.Child.Main
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeComponent.Child.TransactionCreator
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

    fun onIncomeClick(transaction: Transaction)

    fun onNavigateBack()

    fun onRefreshList()

    sealed interface Child {
        data class Main(val component: IncomeComponent) : Child

        data class History(val component: HistoryComponent) : Child

        data class TransactionCreator(val component: TransactionCreationComponent) : Child
    }

    class DefaultIncomeComponent
    @AssistedInject
    constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val incomeStoreFactory: IncomeStoreFactory,
        private val historyComponentFactory: HistoryComponent.Factory,
        private val transactionComponentFactory: TransactionCreationComponent.Factory,
        @Named("usecaseSortedIncome") private val getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
        @Named("usecaseCategoriesIncome") private val getIncomeCategoriesUsecase: GetTypedCategoriesUsecase,
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
                Config.Main -> Main(this)
                Config.History ->
                    History(
                        historyComponentFactory.create(
                            componentContext = componentContext,
                            isIncome = true,
                            getSortedTransactionsUsecase = getSortedTransactionsUsecase,
                            onTransactionSelected = { transaction ->
                                onIncomeClick(transaction)
                            }
                        ),
                    )

                is Config.TransactionCreator -> {
                    TransactionCreator(
                        transactionComponentFactory.create(
                            componentContext = componentContext,
                            launchMode = if (config.transaction == null) {
                                TransactionCreationStore.LaunchMode.CREATING
                            } else {
                                TransactionCreationStore.LaunchMode.EDITING
                            },
                            transaction = config.transaction,
                            onFinish = {
                                onNavigateBack()
                            },
                            getTypedCategories = getIncomeCategoriesUsecase,
                        )
                    )
                }
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
            stateKeeper.register(STATE_KEY, IncomeStore.IncomeScreenState.serializer()) {
                state.value
            }

            lifecycle.doOnResume {
                if (state.value is IncomeStore.IncomeScreenState.Failed) {
                    onRefreshList()
                }
            }
        }

        override fun onRefreshList() {
            store.accept(IncomeStore.Intent.LoadIncome)
        }

        override fun onNavigateBack() {
            stack.pop()
        }

        override fun onHistoryClick() {
            stack.push(Config.History)
        }

        override fun onIncomeClick(transaction: Transaction) {
            stack.push(
                Config.TransactionCreator(
                    isFromIncome = true,
                    transaction,
                )
            )
        }

        override fun onFabClick() {
            stack.push(Config.TransactionCreator(isFromIncome = true, transaction = null))
//                store.accept(IncomeStore.Intent.AddIncome)
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

            @Serializable
            data class TransactionCreator(
                val isFromIncome: Boolean,
                val transaction: Transaction? = null,
            ) : Config()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultIncomeComponent
    }
}
