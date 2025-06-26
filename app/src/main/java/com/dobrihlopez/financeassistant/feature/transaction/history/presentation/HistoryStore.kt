package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.usecase.account.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Store-компонент для экрана истории транзакций — реализует MVI-подход.
 *
 * Основные обязанности:
 * - обработка интентов:
 *   - Init(isIncome): установка типа транзакций (доход/расход),
 *   - ChangeStartDate / ChangeEndDate: изменение фильтра по дате,
 *   - Refresh: обновление списка по текущим фильтрам;
 * - загрузка данных через Executor:
 *   - получает аккаунт пользователя через
 *   @see GetFirstAccountUseCase,
 *   - получает отсортированные транзакции за период через
 *   @see GetSortedTransactionsUsecase,
 *   - переключает состояния загрузки, успешного получения и ошибок;
 * - обновление экрана через Reducer, с которым передаются даты, транзакции и флаг isIncome.
 *
 * Используются:
 * - Bootstrapper для начальной инициализации state,
 * - CoroutineExecutor + CoroutineBootstrapper для асинхронной работы,
 * - Reducer для управления состоянием `State`.
 *
 * Состояние (`State`):
 * - isLoading — флаг загрузки,
 * - errorResId — ресурс ошибки,
 * - transactions — список транзакций,
 * - startDate / endDate — фильтруемый период,
 * - isIncome — тип транзакций (доходы/расходы),
 * - summaryValue, startText, endText — вычисляемые поля для UI.
 *
 * @see Store
 * @see CoroutineBootstrapper
 * @see CoroutineExecutor
 */
interface HistoryStore : Store<HistoryStore.Intent, HistoryStore.State, Nothing> {
    @Serializable
    data class State(
        val isLoading: Boolean = false,
        val errorResId: Int? = null,
        val transactions: List<Transaction> = emptyList(),
        @Contextual val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
        @Contextual val endDate: LocalDate = LocalDate.now(),
        val isIncome: Boolean = true,
    ) {
        val summaryValue: String
            get() = transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
        val startText: String get() = startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        val endText: String get() = endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    }

    sealed class Intent {
        data class Init(val isIncome: Boolean) : Intent()

        data class ChangeStartDate(val date: LocalDate) : Intent()

        data class ChangeEndDate(val date: LocalDate) : Intent()

        data object Refresh : Intent()
    }

    class HistoryStoreFactory
        @Inject
        constructor(
            private val storeFactory: StoreFactory,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val coroutineDispatchers: CoroutineDispatchers,
        ) {
            fun create(
                isIncome: Boolean,
                getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
            ): HistoryStore =
                HistoryStoreImpl(
                    storeFactory,
                    getSortedTransactionsUsecase,
                    getFirstAccountUseCase,
                    coroutineDispatchers,
                    isIncome,
                )

            private class HistoryStoreImpl(
                storeFactory: StoreFactory,
                private val getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
                private val getFirstAccountUseCase: GetFirstAccountUseCase,
                private val coroutineDispatchers: CoroutineDispatchers,
                private val isIncome: Boolean,
            ) : HistoryStore,
                Store<Intent, State, Nothing> by storeFactory.create(
                    name = "HistoryStore",
                    initialState = State(isIncome = isIncome),
                    bootstrapper = BootstrapperImpl(isIncome),
                    executorFactory = {
                        ExecutorImpl(
                            getSortedTransactionsUsecase,
                            getFirstAccountUseCase,
                            coroutineDispatchers,
                        )
                    },
                    reducer = ReducerImpl,
                )

            private class BootstrapperImpl(private val isIncome: Boolean) :
                CoroutineBootstrapper<Action>() {
                override fun invoke() {
                    dispatch(Action.Init(isIncome))
                }
            }

            sealed class Action {
                data class Init(val isIncome: Boolean) : Action()
            }

            private class ExecutorImpl(
                private val getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
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
                        is Intent.Init ->
                            loadHistory(
                                state().startDate,
                                state().endDate,
                                intent.isIncome,
                            )

                        is Intent.ChangeStartDate ->
                            loadHistory(
                                intent.date,
                                state().endDate,
                                state().isIncome,
                            )

                        is Intent.ChangeEndDate ->
                            loadHistory(
                                state().startDate,
                                intent.date,
                                state().isIncome,
                            )

                        Intent.Refresh ->
                            loadHistory(
                                state().startDate,
                                state().endDate,
                                state().isIncome,
                            )
                    }
                }

                private fun loadHistory(
                    startDate: LocalDate,
                    endDate: LocalDate,
                    isIncome: Boolean,
                ) {
                    scope.launch {
                        dispatch(Message.Loading)
                        try {
                            val account =
                                getFirstAccountUseCase() ?: run {
                                    dispatch(Message.Failed())
                                    return@launch
                                }
                            val start = startDate.toString()
                            val end = endDate.toString()
                            val transactions =
                                withContext(coroutineDispatchers.default) {
                                    getSortedTransactionsUsecase(
                                        accountId = account.id,
                                        startDate = start,
                                        endDate = end,
                                    )
                                }
                            dispatch(Message.Succeeded(transactions, startDate, endDate, isIncome))
                        } catch (e: Exception) {
                            dispatch(Message.Failed())
                        }
                    }
                }
            }

            private object ReducerImpl : Reducer<State, Message> {
                override fun State.reduce(msg: Message): State =
                    when (msg) {
                        is Message.Loading -> copy(isLoading = true, errorResId = null)
                        is Message.Failed -> copy(isLoading = false, errorResId = msg.errorResId)
                        is Message.Succeeded ->
                            copy(
                                isLoading = false,
                                errorResId = null,
                                transactions = msg.transactions,
                                startDate = msg.startDate,
                                endDate = msg.endDate,
                                isIncome = msg.isIncome,
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
                    val startDate: LocalDate,
                    val endDate: LocalDate,
                    val isIncome: Boolean,
                ) : Message()
            }
        }
}
