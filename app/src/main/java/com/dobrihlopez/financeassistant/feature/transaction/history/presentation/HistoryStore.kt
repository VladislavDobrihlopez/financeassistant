package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.feature.accounts.domain.usecase.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.transaction.core.GetTransactionsForPeriodUseCase
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.collections.sortedByDescending

interface HistoryStore : Store<HistoryStore.Intent, HistoryStore.State, Nothing> {
    @Serializable
    data class State(
        val isLoading: Boolean = false,
        val errorResId: Int? = null,
        val transactions: List<Transaction> = emptyList(),
        @Contextual val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
        @Contextual val endDate: LocalDate = LocalDate.now(),
        val isIncome: Boolean = true
    ) {
        val summaryValue: String get() = transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
        val startText: String get() = startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        val endText: String get() = endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    }

    sealed class Intent {
        data class Init(val isIncome: Boolean) : Intent()
        data class ChangeStartDate(val date: LocalDate) : Intent()
        data class ChangeEndDate(val date: LocalDate) : Intent()
        data object Refresh : Intent()
    }

    class HistoryStoreFactory @Inject constructor(
        private val storeFactory: StoreFactory,
        private val getFirstAccountUseCase: GetFirstAccountUseCase,
        private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
        private val coroutineDispatchers: CoroutineDispatchers,
    ) {
        fun create(isIncome: Boolean): HistoryStore =
            HistoryStoreImpl(
                storeFactory,
                getTransactionsForPeriodUseCase,
                getFirstAccountUseCase,
                coroutineDispatchers,
                isIncome
            )

        private class HistoryStoreImpl(
            storeFactory: StoreFactory,
            private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val coroutineDispatchers: CoroutineDispatchers,
            private val isIncome: Boolean
        ) : HistoryStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "HistoryStore",
            initialState = State(isIncome = isIncome),
            bootstrapper = BootstrapperImpl(isIncome),
            executorFactory = { ExecutorImpl(getTransactionsForPeriodUseCase, getFirstAccountUseCase, coroutineDispatchers) },
            reducer = ReducerImpl
        )

        private class BootstrapperImpl(private val isIncome: Boolean) : CoroutineBootstrapper<Action>() {
            override fun invoke() {
                dispatch(Action.Init(isIncome))
            }
        }

        sealed class Action {
            data class Init(val isIncome: Boolean) : Action()
        }

        private class ExecutorImpl(
            private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val coroutineDispatchers: CoroutineDispatchers,
        ) : CoroutineExecutor<Intent, Action, State, Message, Nothing>() {
            override fun executeAction(action: Action) {
                when (action) {
                    is Action.Init -> executeIntent(Intent.Init(action.isIncome))
                }
            }
            override fun executeIntent(intent: Intent) {
                when (intent) {
                    is Intent.Init -> loadHistory(state().startDate, state().endDate, intent.isIncome)
                    is Intent.ChangeStartDate -> loadHistory(intent.date, state().endDate, state().isIncome)
                    is Intent.ChangeEndDate -> loadHistory(state().startDate, intent.date, state().isIncome)
                    Intent.Refresh -> loadHistory(state().startDate, state().endDate, state().isIncome)
                }
            }
            private fun loadHistory(startDate: LocalDate, endDate: LocalDate, isIncome: Boolean) {
                scope.launch {
                    dispatch(Message.Loading)
                    try {
                        val account = getFirstAccountUseCase() ?: run {
                            dispatch(Message.Failed())
                            return@launch
                        }
                        val start = startDate.toString()
                        val end = endDate.toString()
                        val transactions = withContext(coroutineDispatchers.default) {
                            getTransactionsForPeriodUseCase(
                                accountId = account.id,
                                startDate = start,
                                endDate = end
                            ).filter { it.category.isIncome == isIncome }
                                .sortedByDescending {
                                    OffsetDateTime.parse(it.updatedAt).toLocalDateTime()
                                }
                        }
                        dispatch(Message.Succeeded(transactions, startDate, endDate, isIncome))
                    } catch (e: Exception) {
                        dispatch(Message.Failed())
                    }
                }
            }
        }

        private object ReducerImpl : Reducer<State, Message> {
            override fun State.reduce(msg: Message): State = when (msg) {
                is Message.Loading -> copy(isLoading = true, errorResId = null)
                is Message.Failed -> copy(isLoading = false, errorResId = msg.errorResId)
                is Message.Succeeded -> copy(
                    isLoading = false,
                    errorResId = null,
                    transactions = msg.transactions,
                    startDate = msg.startDate,
                    endDate = msg.endDate,
                    isIncome = msg.isIncome
                )
            }
        }

        sealed class Message {
            data object Loading : Message()
            data class Failed(@StringRes val errorResId: Int? = null) : Message()
            data class Succeeded(
                val transactions: List<Transaction>,
                val startDate: LocalDate,
                val endDate: LocalDate,
                val isIncome: Boolean
            ) : Message()
        }
    }
}
