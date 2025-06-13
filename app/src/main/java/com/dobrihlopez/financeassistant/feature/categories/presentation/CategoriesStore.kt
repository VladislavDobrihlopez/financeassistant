package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.annotation.StringRes
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
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
    }

    class CategoriesStoreFactory(
        private val storeFactory: StoreFactory
    ) {
        fun create(initialState: CategoriesScreenState): CategoriesStore =
            object :
                CategoriesStore,
                Store<Intent, CategoriesScreenState, Nothing> by storeFactory.create(
                    name = "CategoriesStore",
                    initialState = initialState,
                    executorFactory =  { ExecutorImpl() },
                    reducer = ReducerImpl
                ) {}

        private class ExecutorImpl: CoroutineExecutor<Intent, Nothing, CategoriesScreenState, Message, Nothing>() {
            override fun executeIntent(intent: Intent) {
                super.executeIntent(intent)

                when (intent) {
                    is Intent.SearchBarTextChange -> dispatch(Message.UpdateSearchBar(intent.text))
                    Intent.SearchCategories -> {}
                }
            }
        }

        private object ReducerImpl: Reducer<CategoriesScreenState, Message> {
            override fun CategoriesScreenState.reduce(
                msg: Message,
            ): CategoriesScreenState {
                if (this !is CategoriesScreenState.Succeeded) return this

                return when (msg) {
                    is Message.UpdateSearchBar -> copy(searchText = msg.text.trimStart())
                    is Message.UpdateCategories -> copy(categories = provideCategories())
                }
            }
        }

        sealed class Message {
            data class UpdateSearchBar(val text: String): Message()
            data class UpdateCategories(val categories: List<Category>): Message()
        }
    }
}
