package com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.transaction_core.core.previewIncomeTransactions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import com.dobrihlopez.financeassistant.core.domain.income.IncomeRepository
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeStore.IncomeStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

interface IncomeComponent {
    val state: StateFlow<IncomeStore.IncomeScreenState>

    fun onHistoryClick()
    fun onFabClick()

    class DefaultIncomeComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val incomeStoreFactory: IncomeStoreFactory
    ) : IncomeComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = IncomeStore.IncomeScreenState.serializer())
            ?: IncomeStore.IncomeScreenState.Succeeded(
                transactions = previewIncomeTransactions(),
                summaryText = "Всего",
                summaryValue = "900 000 ₽"
            )

        private val store = instanceKeeper.getStore {
            incomeStoreFactory.create(initState)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<IncomeStore.IncomeScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("income_state", IncomeStore.IncomeScreenState.serializer()) {
                state.value
            }
        }

        override fun onHistoryClick() {
            store.accept(IncomeStore.Intent.HistoryClick)
        }

        override fun onFabClick() {
            store.accept(IncomeStore.Intent.AddIncome)
        }

        private companion object {
            const val STATE_KEY = "income"
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultIncomeComponent
    }
}
