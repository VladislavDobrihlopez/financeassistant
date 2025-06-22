package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.accounts.domain.usecase.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core.usecase.GetTransactionsForPeriodUseCase
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate
import javax.inject.Inject

interface ExpenseStore : Store<ExpenseStore.Intent, ExpenseStore.ExpenseScreenState, Nothing> {
    @Serializable
    sealed class ExpenseScreenState {
        @Serializable
        data object Loading : ExpenseScreenState()

        @Serializable
        data class Failed(
            @StringRes val errorResId: Int? = null,
        ) : ExpenseScreenState()

        @Serializable
        data class Succeeded(
            val transactions: List<Transaction>,
            val summaryValue: String,
        ) : ExpenseScreenState()
    }

    sealed class Intent {
        data object LoadExpenses : Intent()

        data object AddExpense : Intent()

        data object HistoryClick : Intent()

        data class OnExpenseClick(val transaction: Transaction) : Intent()
    }

    class ExpenseStoreFactory
        @Inject
        constructor(
            private val storeFactory: StoreFactory,
            private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
        ) {
            fun create(initialState: ExpenseScreenState): ExpenseStore =
                ExpenseStoreImpl(
                    storeFactory,
                    initialState,
                    getTransactionsForPeriodUseCase,
                    getFirstAccountUseCase,
                )

            private class ExpenseStoreImpl(
                storeFactory: StoreFactory,
                initialState: ExpenseScreenState,
                private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
                private val getFirstAccountUseCase: GetFirstAccountUseCase,
            ) : ExpenseStore,
                Store<Intent, ExpenseScreenState, Nothing> by storeFactory.create(
                    name = "ExpenseStore",
                    initialState = initialState,
                    bootstrapper = BootstrapperImpl(),
                    executorFactory = {
                        ExecutorImpl(
                            getTransactionsForPeriodUseCase,
                            getFirstAccountUseCase,
                        )
                    },
                    reducer = ReducerImpl,
                )

            private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
                override fun invoke() {
                    dispatch(Action.LoadExpenses)
                }
            }

            sealed class Action {
                data object LoadExpenses : Action()
            }

            private class ExecutorImpl(
                private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
                private val getFirstAccountUseCase: GetFirstAccountUseCase,
            ) : CoroutineExecutor<Intent, Action, ExpenseScreenState, Message, Nothing>() {
                override fun executeAction(action: Action) {
                    when (action) {
                        Action.LoadExpenses -> executeIntent(Intent.LoadExpenses)
                    }
                }

                override fun executeIntent(intent: Intent) {
                    when (intent) {
                        Intent.LoadExpenses -> loadExpensesToday()
                        Intent.AddExpense -> {}
                        Intent.HistoryClick -> {}
                        is Intent.OnExpenseClick -> {}
                    }
                }

                private fun loadExpensesToday() {
                    scope.launch {
                        try {
                            dispatch(Message.Loading)
                            val account =
                                getFirstAccountUseCase() ?: run {
                                    dispatch(Message.Failed())
                                    return@launch
                                }
                            val today = LocalDate.now().toString()
                            val transactions =
                                getTransactionsForPeriodUseCase(
                                    accountId = account.id,
                                    startDate = today,
                                    endDate = today,
                                ).filter { !it.category.isIncome }
                            val summaryValue =
                                transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
                            dispatch(Message.Succeeded(transactions, summaryValue))
                        } catch (e: Exception) {
                            dispatch(Message.Failed())
                        }
                    }
                }
            }

            private object ReducerImpl : Reducer<ExpenseScreenState, Message> {
                override fun ExpenseScreenState.reduce(msg: Message): ExpenseScreenState {
                    return when (msg) {
                        is Message.Loading -> ExpenseScreenState.Loading
                        is Message.Failed -> ExpenseScreenState.Failed(msg.errorResId)
                        is Message.Succeeded ->
                            ExpenseScreenState.Succeeded(
                                transactions = msg.transactions,
                                summaryValue = msg.summaryValue,
                            )
                    }
                }
            }

            private sealed class Message {
                data object Loading : Message()

                data class Failed(
                    @StringRes val errorResId: Int? = null,
                ) : Message()

                data class Succeeded(
                    val transactions: List<Transaction>,
                    val summaryValue: String,
                ) : Message()
            }
        }
}
