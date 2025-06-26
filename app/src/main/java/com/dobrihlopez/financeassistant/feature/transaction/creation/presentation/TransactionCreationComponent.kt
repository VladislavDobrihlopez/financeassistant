package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.core.componentScope
import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

interface TransactionCreationComponent: ComponentContext {
    val state: StateFlow<TransactionCreationStore.State>
    val labels: Flow<TransactionCreationStore.Label>

    fun updateCategory(category: Category)
    fun updateSum(newSum: String)
    fun updateDate(newDate: LocalDate)
    fun updateTime(newTime: LocalTime)
    fun updateComment(text: String)
    fun applyChanges()
    fun deleteTransaction()

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("launchMode") launchMode: TransactionCreationStore.LaunchMode,
            @Assisted("transaction") transaction: Transaction?,
            @Assisted("lambdaFinish") onFinish: () -> Unit,
            @Assisted("usecaseCategories") getTypedCategories: GetTypedCategoriesUsecase,
        ): DefaultTransactionCreationComponent
    }

    class DefaultTransactionCreationComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        @Assisted("launchMode") private val launchMode: TransactionCreationStore.LaunchMode,
        @Assisted("transaction") private val transaction: Transaction?,
        @Assisted("lambdaFinish") onFinish: () -> Unit,
        @Assisted("usecaseCategories") getTypedCategories: GetTypedCategoriesUsecase,
        private val transactionStoreFactory: TransactionCreationStore.TransactionStoreFactory,
    ): TransactionCreationComponent, ComponentContext by componentContext {

        private fun provideInitState(): TransactionCreationStore.State {
            val initState = when (launchMode) {
                TransactionCreationStore.LaunchMode.EDITING -> {
                    requireNotNull(transaction)
                    TransactionCreationStore.State.Success(
                        mode = TransactionCreationStore.LaunchMode.EDITING,
                        categories = listOf(),
                        chosenCategory = transaction.category,
                        sum = transaction.amount,
                        date = OffsetDateTime.parse(transaction.transactionDate).toLocalDateTime(),
                        comment = transaction.comment,
                        originalTransaction = transaction

                    )
                }
                TransactionCreationStore.LaunchMode.CREATING -> {
                    TransactionCreationStore.State.Success(
                        mode = TransactionCreationStore.LaunchMode.CREATING,
                        categories = listOf(),
                        chosenCategory = null,
                        sum = "0.0",
                        date = LocalDateTime.now(),
                        comment = "",
                        originalTransaction = null

                    )
                }
            }
            return initState
        }

        private val restoredState =
            stateKeeper.consume(STATE_KEY, strategy = TransactionCreationStore.State.serializer()) ?: provideInitState()


        private val store = instanceKeeper.getStore {
            transactionStoreFactory.create(initState = restoredState, getTypedCategories = getTypedCategories)
        }

        init {
            stateKeeper.register(STATE_KEY, strategy = TransactionCreationStore.State.serializer()) {
                state.value
            }

            componentScope().launch {
                labels.collect {
                    onFinish()
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<TransactionCreationStore.State>
            get() = store.stateFlow

        override val labels: Flow<TransactionCreationStore.Label>
            get() = store.labels

        override fun updateCategory(category: Category) {
            store.accept(TransactionCreationStore.Intent.UpdateCategory(category))
        }

        override fun updateSum(newSum: String) {
            store.accept(TransactionCreationStore.Intent.UpdateSum(newSum))
        }

        override fun updateDate(newDate: LocalDate) {
            store.accept(TransactionCreationStore.Intent.UpdateDate(newDate))
        }

        override fun updateTime(newTime: LocalTime) {
            store.accept(TransactionCreationStore.Intent.UpdateTime(newTime))
        }

        override fun updateComment(text: String) {
            store.accept(TransactionCreationStore.Intent.UpdateComment(text))
        }

        override fun applyChanges() {
            store.accept(TransactionCreationStore.Intent.Apply)
        }

        override fun deleteTransaction() {
            store.accept(TransactionCreationStore.Intent.DeleteTransaction)
        }

        private companion object {
            const val STATE_KEY = "transaction_creation"
        }
    }
}
