package com.dobrihlopez.financeassistant.feature.categories.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface CategoriesComponent {
    val state: StateFlow<CategoriesScreenState>

    fun onSearchBarTextChange(text: String)
    fun onSearchClick()

    class DefaultCategoriesComponent(
        val componentContext: ComponentContext,
        private val storeFactory: StoreFactory,
    ): CategoriesComponent, ComponentContext by componentContext {

        private fun restoreState() = stateKeeper.consume(STATE_KEY, strategy = CategoriesScreenState.serializer())

        private val initState = restoreState() ?: CategoriesScreenState.Succeeded(
            searchText = "",
            categories = provideCategories()
        )

        private val store = instanceKeeper.getStore {
            CategoriesStore.CategoriesStoreFactory(storeFactory).create(
                initialState = initState
            )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override val state: StateFlow<CategoriesScreenState>
            get() = store.stateFlow

        init {
            stateKeeper.register("categories_state", CategoriesScreenState.serializer()) {
                state.value
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
}

fun provideCategories(): List<Category> = listOf(
    Category(id = 1, emoji = "🏠", isIncome = false, name = "Аренда квартиры"),
    Category(id = 2, emoji = "👗", isIncome = false, name = "Одежда"),
    Category(id = 3, emoji = "🐶", isIncome = false, name = "На собачку"),
    Category(id = 4, emoji = "🐶", isIncome = false, name = "На собачку"),
    Category(
        id = 5,
        emoji = "🟢",
        isIncome = false,
        name = "Ремонт квартиры"
    ),
    Category(id = 6, emoji = "🍭", isIncome = false, name = "Продукты"),
    Category(id = 7, emoji = "🤸‍♂️", isIncome = false, name = "Спортзал"),
    Category(id = 8, emoji = "💊", isIncome = false, name = "Медицина")
)
