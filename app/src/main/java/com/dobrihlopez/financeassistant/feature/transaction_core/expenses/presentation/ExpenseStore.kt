package com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.Transaction
import kotlinx.serialization.Serializable

interface ExpenseStore: Store<ExpenseStore.Intent, ExpenseStore.ExpenseScreenState, Nothing> {
    @Serializable
    sealed class ExpenseScreenState {
        @Serializable
        data object Loading: ExpenseScreenState()
        @Serializable
        data class Failed(@StringRes val errorResId: Int? = null): ExpenseScreenState()
        @Serializable
        data class Succeeded(
            val transactions: List<Transaction>,
            val summaryText: String,
            val summaryValue: String
        ): ExpenseScreenState()
    }

    sealed class Intent {
        data object LoadExpenses: Intent()
        data object AddExpense: Intent()
        data object HistoryClick: Intent()
        data class OnExpenseClick(val transaction: Transaction): Intent()
    }

    class ExpenseStoreFactory(
        private val storeFactory: StoreFactory
    ) {
        fun create(initialState: ExpenseScreenState): ExpenseStore =
            object :
                ExpenseStore,
                Store<Intent, ExpenseScreenState, Nothing> by storeFactory.create(
                    name = "ExpenseStore",
                    initialState = initialState,
                    executorFactory = { ExecutorImpl() },
                    reducer = ReducerImpl
                ) {}

        private class ExecutorImpl: CoroutineExecutor<Intent, Nothing, ExpenseScreenState, Message, Nothing>() {
            override fun executeIntent(intent: Intent) {
                when (intent) {
                    Intent.LoadExpenses -> {}
                    Intent.AddExpense -> {}
                    Intent.HistoryClick -> {}
                    is Intent.OnExpenseClick -> {}
                }
            }
        }

        private object ReducerImpl: Reducer<ExpenseScreenState, Message> {
            override fun ExpenseScreenState.reduce(
                msg: Message,
            ): ExpenseScreenState {
                return when (msg) {
                    is Message.Loading -> ExpenseScreenState.Loading
                    is Message.Failed -> ExpenseScreenState.Failed(msg.errorResId)
                    is Message.Succeeded -> ExpenseScreenState.Succeeded(
                        transactions = msg.transactions,
                        summaryText = msg.summaryText,
                        summaryValue = msg.summaryValue
                    )
                }
            }
        }

        sealed class Message {
            data object Loading: Message()
            data class Failed(@StringRes val errorResId: Int? = null): Message()
            data class Succeeded(
                val transactions: List<Transaction>,
                val summaryText: String,
                val summaryValue: String
            ): Message()
        }
    }
} 