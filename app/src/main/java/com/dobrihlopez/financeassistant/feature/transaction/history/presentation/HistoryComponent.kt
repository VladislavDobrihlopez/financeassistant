package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryStore.Intent
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryStore.State
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface HistoryComponent {
    val state: StateFlow<State>
    fun onStartDateClick(date: LocalDate)
    fun onEndDateClick(date: LocalDate)
    fun onRefresh()

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("isIncome") isIncome: Boolean,
            @Assisted("usecase") getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
        ): DefaultHistoryComponent
    }

    class DefaultHistoryComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        @Assisted("isIncome") private val isIncome: Boolean,
        @Assisted("usecase") private val getSortedTransactionsUsecase: GetSortedTransactionsUsecase,
        private val storeFactory: HistoryStore.HistoryStoreFactory,
    ) : HistoryComponent, ComponentContext by componentContext {

        private val store = instanceKeeper.getStore {
            storeFactory.create(isIncome, getSortedTransactionsUsecase)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<State>
            get() = store.stateFlow

        override fun onStartDateClick(date: LocalDate) {
            store.accept(Intent.ChangeStartDate(date))
        }

        override fun onEndDateClick(date: LocalDate) {
            store.accept(Intent.ChangeEndDate(date))
        }

        override fun onRefresh() {
            store.accept(Intent.Refresh)
        }
    }
}
