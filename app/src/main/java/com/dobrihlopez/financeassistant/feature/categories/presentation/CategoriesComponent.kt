package com.dobrihlopez.financeassistant.feature.categories.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesScreenState
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface CategoriesComponent {
    val state: StateFlow<CategoriesScreenState>

    fun onSearchBarTextChange(text: String)

    fun onSearchClick()

    class DefaultCategoriesComponent
        @AssistedInject
        constructor(
            @Assisted("componentContext") private val componentContext: ComponentContext,
            private val categoriesStoreFactory: CategoriesStoreFactory,
        ) : CategoriesComponent, ComponentContext by componentContext {
            private val initState =
                stateKeeper.consume(STATE_KEY, strategy = CategoriesScreenState.serializer())
                    ?: CategoriesScreenState.Loading

            private val store =
                instanceKeeper.getStore {
                    categoriesStoreFactory.create(initState)
                }

            @OptIn(ExperimentalCoroutinesApi::class)
            override val state: StateFlow<CategoriesScreenState>
                get() = store.stateFlow

            init {
                stateKeeper.register(STATE_KEY, CategoriesScreenState.serializer()) {
                    state.value
                }

                lifecycle.doOnStart {
                    if (state.value is CategoriesScreenState.Failed) {
                        store.accept(CategoriesStore.Intent.RefreshList)
                    }
                }
            }

            override fun onSearchBarTextChange(text: String) {
                store.accept(CategoriesStore.Intent.SearchBarTextChange(text))
            }

            override fun onSearchClick() {
                store.accept(CategoriesStore.Intent.SearchCategories)
            }

            private companion object {
                const val STATE_KEY = "categories"
            }
        }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultCategoriesComponent
    }
}
