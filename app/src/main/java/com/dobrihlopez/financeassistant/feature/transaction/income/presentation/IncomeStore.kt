package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.usecase.account.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core.usecase.GetTransactionsForPeriodUseCase
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface IncomeStore : Store<IncomeStore.Intent, IncomeStore.IncomeScreenState, Nothing> {
    @Serializable
    sealed class IncomeScreenState {
        @Serializable
        data object Loading : IncomeScreenState()

        @Serializable
        data class Failed(
            @StringRes val errorResId: Int? = null,
        ) : IncomeScreenState()

        @Serializable
        data class Succeeded(
            val transactions: List<Transaction>,
            val summaryValue: String,
        ) : IncomeScreenState()
    }

    sealed class Intent {
        data object LoadIncome : Intent()

        data object AddIncome : Intent()

        data object HistoryClick : Intent()
    }

    class IncomeStoreFactory
        @Inject
        constructor(
            private val storeFactory: StoreFactory,
            private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
        ) {
            fun create(initialState: IncomeScreenState): IncomeStore =
                IncomeStoreImpl(
                    storeFactory,
                    initialState,
                    getTransactionsForPeriodUseCase,
                    getFirstAccountUseCase,
                )

            private class IncomeStoreImpl(
                storeFactory: StoreFactory,
                initialState: IncomeScreenState,
                private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
                private val getFirstAccountUseCase: GetFirstAccountUseCase,
            ) : IncomeStore,
                Store<Intent, IncomeScreenState, Nothing> by storeFactory.create(
                    name = "IncomeStore",
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
                    dispatch(Action.LoadIncome)
                }
            }

            sealed class Action {
                data object LoadIncome : Action()
            }

            private class ExecutorImpl(
                private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
                private val getFirstAccountUseCase: GetFirstAccountUseCase,
            ) : CoroutineExecutor<Intent, Action, IncomeScreenState, Message, Nothing>() {
                override fun executeAction(action: Action) {
                    when (action) {
                        Action.LoadIncome -> executeIntent(Intent.LoadIncome)
                    }
                }

                override fun executeIntent(intent: Intent) {
                    when (intent) {
                        Intent.LoadIncome -> loadIncomeToday()
                        Intent.AddIncome -> {}
                        Intent.HistoryClick -> {}
                    }
                }

                private fun loadIncomeToday() {
                    scope.launch {
                        try {
                            dispatch(Message.Loading)
                            val account =
                                getFirstAccountUseCase() ?: run {
                                    dispatch(Message.Failed())
                                    return@launch
                                }
                            val today = LocalDate.now()
                            val transactions =
                                getTransactionsForPeriodUseCase(
                                    accountId = account.id,
                                    startDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE),
                                    endDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE),
                                ).filter { it.category.isIncome }
                            val summaryValue =
                                transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
                            dispatch(Message.Succeeded(transactions, summaryValue))
                        } catch (e: Exception) {
                            dispatch(Message.Failed())
                        }
                    }
                }
            }

            private object ReducerImpl : Reducer<IncomeScreenState, Message> {
                override fun IncomeScreenState.reduce(msg: Message) =
                    when (msg) {
                        is Message.Loading -> IncomeScreenState.Loading
                        is Message.Failed -> IncomeScreenState.Failed(msg.errorResId)
                        is Message.Succeeded ->
                            IncomeScreenState.Succeeded(
                                transactions = msg.transactions,
                                summaryValue = msg.summaryValue,
                            )
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
