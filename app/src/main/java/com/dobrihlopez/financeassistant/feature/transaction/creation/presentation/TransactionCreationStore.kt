package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.core.usecase.account.GetFirstAccountUseCase
import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase.CreateTransactionUseCase
import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase.DeleteTransactionUseCase
import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase.UpdateTransactionUseCase
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.State
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.TransactionStoreFactory.Action.LoadCategories
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.TransactionStoreFactory.Message.UpdateAmount
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.TransactionStoreFactory.Message.UpdateChosenCategory
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.TransactionStoreFactory.Message.UpdateComment
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore.TransactionStoreFactory.Message.UpdateTransactionDate
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface TransactionCreationStore : Store<TransactionCreationStore.Intent, State, TransactionCreationStore.Label> {

    enum class LaunchMode {
        EDITING,
        CREATING,
    }

    sealed interface Label {
        data object ChangesSuccessfullyApplied: Label
        data object SuccessfullyCreated: Label
        data object SuccessfullyDeleted: Label
    }

    sealed interface Intent {
        data class UpdateCategory(val category: Category) : Intent
        data class UpdateSum(val newSum: String) : Intent
        data class UpdateDate(val newDate: LocalDate) : Intent
        data class UpdateTime(val newTime: LocalTime) : Intent
        data class UpdateComment(val text: String) : Intent
        data object Apply : Intent
        data object DeleteTransaction : Intent
    }

    @Serializable
    sealed class State {
        @Serializable
        data object Loading : State()
        @Serializable
        data object Failed : State()
        @Serializable
        data class Success(
            val mode: LaunchMode,
            val categories: List<Category>,
            val chosenCategory: Category? = null,
            val sum: String,
            @Contextual val date: LocalDateTime? = null,
            val comment: String,
            val originalTransaction: Transaction? = null, // can be null when creating a new one
        ) : State()
    }

    class TransactionStoreFactory @Inject constructor(
        private val storeFactory: StoreFactory,
        private val createTransactionUseCase: CreateTransactionUseCase,
        private val updateTransactionUseCase: UpdateTransactionUseCase,
        private val deleteTransactionUseCase: DeleteTransactionUseCase,
        private val getFirstAccountUseCase: GetFirstAccountUseCase,
    ) {
        fun create(initState: State, getTypedCategories: GetTypedCategoriesUsecase): TransactionCreationStore {
            return TransactionStoreFactoryImpl(
                storeFactory, createTransactionUseCase,
                updateTransactionUseCase,
                deleteTransactionUseCase,
                getFirstAccountUseCase,
                getTypedCategories,
                initState,
            )
        }

        private class TransactionStoreFactoryImpl(
            private val storeFactory: StoreFactory,
            private val createTransactionUseCase: CreateTransactionUseCase,
            private val updateTransactionUseCase: UpdateTransactionUseCase,
            private val deleteTransactionUseCase: DeleteTransactionUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
            private val getTypedCategories: GetTypedCategoriesUsecase,
            private val initState: State,
        ) : TransactionCreationStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "TransactionStore",
                initialState = initState,
                bootstrapper = BootstrapperImpl(getTypedCategories),
                executorFactory = {
                    ExecutorImpl(
                        createTransactionUseCase,
                        updateTransactionUseCase,
                        deleteTransactionUseCase,
                        getFirstAccountUseCase,
                    )
                },
                reducer = ReducerImpl
            )

        private class BootstrapperImpl(
            private val getTypedCategories: GetTypedCategoriesUsecase,
        ): CoroutineBootstrapper<Action>() {
            override fun invoke() {
                scope.launch {
                    val items = getTypedCategories()
                    dispatch(LoadCategories(items))
                }
            }
        }

        private class ExecutorImpl(
            private val createTransactionUseCase: CreateTransactionUseCase,
            private val updateTransactionUseCase: UpdateTransactionUseCase,
            private val deleteTransactionUseCase: DeleteTransactionUseCase,
            private val getFirstAccountUseCase: GetFirstAccountUseCase,
        ) : CoroutineExecutor<Intent, Action, State, Message, Label>() {
            override fun executeAction(action: Action) {
                super.executeAction(action)

                when (action) {
                    is Action.LoadCategories -> {
                        dispatch(Message.UpdateCategoriesList(action.categories))
                        val state = state()
                        if (state is State.Success) {
                            if (state.chosenCategory == null) {
                                dispatch(UpdateChosenCategory(action.categories.first()))
                            }
                        }
                    }
                }
            }

            override fun executeIntent(intent: Intent) {
                super.executeIntent(intent)
                val state = state()

                try {
                    when (intent) {
                        Intent.DeleteTransaction -> {
                            require(state is State.Success)
                            require(state.mode == LaunchMode.EDITING && state.originalTransaction != null) {
                                "Inappropriate use of delete transaction"
                            }
                            scope.launch {
                                deleteTransactionUseCase(state.originalTransaction.id)
                                publish(Label.SuccessfullyDeleted)
                            }
                        }

                        Intent.Apply -> {
                            if (state !is State.Success) return
                            when (state.mode) {
                                LaunchMode.EDITING -> {
                                    scope.launch {
                                        editTransaction(state)
                                        publish(Label.ChangesSuccessfullyApplied)
                                    }
                                }
                                LaunchMode.CREATING -> {
                                    scope.launch {
                                        createNewTransaction(state)
                                        publish(Label.SuccessfullyCreated)
                                    }
                                }
                            }
                        }

                        is Intent.UpdateCategory -> {
                            dispatch(UpdateChosenCategory(intent.category))
                        }

                        is Intent.UpdateComment -> {
                            dispatch(UpdateComment(intent.text))
                        }

                        is Intent.UpdateDate -> {
                            val state = state()
                            require(state is State.Success)
                            val dispatchingDateTime = if (state.date == null) {
                                LocalDateTime.of(intent.newDate, LocalTime.now())
                            } else {
                                LocalDateTime.of(intent.newDate, state.date.toLocalTime())
                            }
                            dispatch(UpdateTransactionDate(dispatchingDateTime))
                        }

                        is Intent.UpdateTime -> {
                            val state = state()
                            require(state is State.Success)
                            val dispatchingDateTime = if (state.date == null) {
                                LocalDateTime.of(LocalDate.now(), intent.newTime)
                            } else {
                                LocalDateTime.of(state.date.toLocalDate(), intent.newTime)
                            }
                            dispatch(UpdateTransactionDate(dispatchingDateTime))
                        }

                        is Intent.UpdateSum -> {
                            dispatch(UpdateAmount(intent.newSum))
                        }
                    }
                } catch (e: Exception) {
                    dispatch(Message.Failed)
                }
            }

            private suspend fun createNewTransaction(state: State) {
                require(state is State.Success)

                val account = getFirstAccountUseCase()
                requireNotNull(account) {
                    "Account is not found"
                }
                requireNotNull(state.chosenCategory) {
                    "Category is not chosen"
                }

                createTransactionUseCase(
                    accountId = account.id,
                    categoryId = state.chosenCategory.id,
                    amount = state.sum,
                    transactionDate = state.date ?: LocalDateTime.now(),
                    comment = state.comment
                )
            }

            private suspend fun editTransaction(state: State) {
                require(state is State.Success)
                val original = state.originalTransaction
                requireNotNull(original) {
                    "Original transaction is not found"
                }

                updateTransactionUseCase(
                    original.copy(
                        amount = state.sum,
                        category = state.chosenCategory ?: original.category,
                        comment = state.comment,
                        transactionDate = state.date!!.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT),
                    )
                )
            }
        }

        private object ReducerImpl: Reducer<State, Message> {
            override fun State.reduce(
                msg: Message,
            ): State {
                if (msg is Message.Failed) return State.Failed
                require(this is State.Success)

                return when (msg) {
                    Message.Failed -> State.Failed
                    is UpdateAmount -> {
                        copy(sum = msg.amount)
                    }
                    is UpdateChosenCategory -> {
                        copy(chosenCategory = msg.category)
                    }
                    is UpdateComment -> {
                        copy(comment = msg.comment)
                    }
                    is UpdateTransactionDate -> {
                        copy(date = msg.date)
                    }

                    is Message.UpdateCategoriesList -> {
                        copy(categories = msg.categories)
                    }
                }
            }
        }

        private sealed interface Action {
            data class LoadCategories(val categories: List<Category>): Action
        }

        private sealed interface Message {
            data class UpdateCategoriesList(val categories: List<Category>) : Message
            data class UpdateChosenCategory(val category: Category) : Message
            data class UpdateComment(val comment: String) : Message
            data class UpdateTransactionDate(val date: LocalDateTime) : Message
            data class UpdateAmount(val amount: String) : Message
            data object Failed : Message
        }
    }
}
