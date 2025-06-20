package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import com.dobrihlopez.financeassistant.core.domain.article.ArticleRepository
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import com.dobrihlopez.financeassistant.feature.categories.domain.GetCategoriesUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.serialization.Serializable

interface CategoriesStore: Store<CategoriesStore.Intent, CategoriesStore.CategoriesScreenState, Nothing> {
    @Serializable
    sealed class CategoriesScreenState {
        @Serializable
        data object Loading: CategoriesScreenState()
        @Serializable
        data class Failed(@StringRes val errorResId: Int? = null): CategoriesScreenState()
        @Serializable
        data class Succeeded(
            val searchText: String,
            val categories: List<Category>
        ): CategoriesScreenState()
    }

    sealed class Intent {
        data class SearchBarTextChange(val text: String): Intent()
        data object SearchCategories: Intent()
        data object RefreshList: Intent()
    }

    class CategoriesStoreFactory @Inject constructor(
        private val storeFactory: StoreFactory,
        private val getCategoriesUsecase: GetCategoriesUsecase,
    ) {

        fun create(initialState: CategoriesScreenState): CategoriesStore =
            CategoriesStoreImpl(storeFactory, initialState, getCategoriesUsecase)

        private class CategoriesStoreImpl(
            storeFactory: StoreFactory,
            initialState: CategoriesScreenState,
            getCategoriesUsecase: GetCategoriesUsecase
        ) : CategoriesStore, Store<Intent, CategoriesScreenState, Nothing> by storeFactory.create(
            name = "CategoriesStore",
            initialState = initialState,
            bootstrapper = BootstrapperImpl(getCategoriesUsecase),
            executorFactory = { ExecutorImpl(getCategoriesUsecase) },
            reducer = ReducerImpl
        )

        private class BootstrapperImpl(
            private val getCategoriesUsecase: GetCategoriesUsecase
        ): CoroutineBootstrapper<Action>() {
            override fun invoke() {
                scope.launch {
                    val items = getCategoriesUsecase()
                    dispatch(Action.LoadedCategoriesList(items))
                }
            }
        }

        private class ExecutorImpl(
            private val getCategoriesUsecase: GetCategoriesUsecase,
        ): CoroutineExecutor<Intent, Action, CategoriesScreenState, Message, Nothing>() {
            private var categories: List<Category> = emptyList()

            override fun executeAction(action: Action) {
                super.executeAction(action)
                when (action) {
                    is Action.LoadedCategoriesList -> {
                        categories = action.categories.toList()
                        dispatch(Message.UpdateCategories(action.categories))
                    }
                }
            }

            override fun executeIntent(intent: Intent) {
                super.executeIntent(intent)
                when (intent) {
                    is Intent.SearchBarTextChange -> dispatch(Message.UpdateSearchBar(intent.text))
                    Intent.SearchCategories -> {
                        val state = state()
                        if (state is CategoriesScreenState.Succeeded) {
                            scope.launch {
                                val filteredCategories = categories.filterByQuery(state.searchText)
                                dispatch(Message.UpdateCategories(filteredCategories))
                            }
                        }
                    }
                    Intent.RefreshList -> {
                        scope.launch {
                            val items = getCategoriesUsecase()
                            executeAction(Action.LoadedCategoriesList(items))
                        }
                    }
                }
            }

            private fun List<Category>.filterByQuery(query: String): List<Category> {
                val optimizedQuery = query.trimStart()
                return if (optimizedQuery.isEmpty())
                    this
                else
                    filter { it.name.contains(other = optimizedQuery, ignoreCase = true) }
            }
        }

        private object ReducerImpl: Reducer<CategoriesScreenState, Message> {
            override fun CategoriesScreenState.reduce(
                msg: Message,
            ): CategoriesScreenState {
                return when (msg) {
                    is Message.UpdateSearchBar -> {
                        if (this is CategoriesScreenState.Succeeded) {
                            copy(searchText = msg.text.trimStart())
                        } else {
                            this
                        }
                    }

                    is Message.UpdateCategories -> {
                        if (this is CategoriesScreenState.Succeeded) {
                            copy(categories = msg.categories)
                        } else {
                            CategoriesScreenState.Succeeded(
                                searchText = "",
                                categories = msg.categories
                            )
                        }
                    }
                }
            }
        }

        private sealed class Action {
            data class LoadedCategoriesList(val categories: List<Category>): Action()
        }

        private sealed class Message {
            data class UpdateSearchBar(val text: String): Message()
            data class UpdateCategories(val categories: List<Category>): Message()
        }
    }
}
