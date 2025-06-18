package com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core.domain.income.IncomeRepository
import javax.inject.Inject
import kotlinx.serialization.Serializable

interface IncomeStore: Store<IncomeStore.Intent, IncomeStore.IncomeScreenState, Nothing> {
    @Serializable
    sealed class IncomeScreenState {
        @Serializable
        data object Loading: IncomeScreenState()
        @Serializable
        data class Failed(@StringRes val errorResId: Int? = null): IncomeScreenState()
        @Serializable
        data class Succeeded(
            val transactions: List<Transaction>,
            val summaryText: String,
            val summaryValue: String
        ): IncomeScreenState()
    }

    sealed class Intent {
        data object LoadIncome: Intent()
        data object AddIncome: Intent()
        data object HistoryClick: Intent()
    }

    class IncomeStoreFactory @Inject constructor(
        private val storeFactory: StoreFactory,
        private val incomeRepository: IncomeRepository
    ) {
        fun create(initialState: IncomeScreenState): IncomeStore =
            IncomeStoreImpl(storeFactory, initialState, incomeRepository)

        private class IncomeStoreImpl(
            storeFactory: StoreFactory,
            initialState: IncomeScreenState,
            incomeRepository: IncomeRepository
        ) : IncomeStore, Store<Intent, IncomeScreenState, Nothing> by storeFactory.create(
            name = "IncomeStore",
            initialState = initialState,
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl
        )

        private class ExecutorImpl: CoroutineExecutor<Intent, Nothing, IncomeScreenState, Message, Nothing>() {
            override fun executeIntent(intent: Intent) {
                when (intent) {
                    Intent.LoadIncome -> {}
                    Intent.AddIncome -> {}
                    Intent.HistoryClick -> {}
                }
            }
        }

        private object ReducerImpl: Reducer<IncomeScreenState, Message> {
            override fun IncomeScreenState.reduce(
                msg: Message,
            ): IncomeScreenState {
                return when (msg) {
                    is Message.Loading -> IncomeScreenState.Loading
                    is Message.Failed -> IncomeScreenState.Failed(msg.errorResId)
                    is Message.Succeeded -> IncomeScreenState.Succeeded(
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