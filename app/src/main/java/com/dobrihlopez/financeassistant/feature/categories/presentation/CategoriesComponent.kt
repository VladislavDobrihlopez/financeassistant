package com.dobrihlopez.financeassistant.feature.categories.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import com.dobrihlopez.financeassistant.core.domain.article.ArticleRepository
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

interface CategoriesComponent {
    val state: StateFlow<CategoriesScreenState>

    fun onSearchBarTextChange(text: String)
    fun onSearchClick()

    class DefaultCategoriesComponent @AssistedInject constructor(
        @Assisted("componentContext") private val componentContext: ComponentContext,
        private val categoriesStoreFactory: CategoriesStoreFactory
    ) : CategoriesComponent, ComponentContext by componentContext {

        private val initState = stateKeeper.consume(STATE_KEY, strategy = CategoriesScreenState.serializer())
            ?: CategoriesScreenState.Loading

        private val store = instanceKeeper.getStore {
            categoriesStoreFactory.create(initState)
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

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultCategoriesComponent
    }
}

// mock data
fun provideCategories(): List<Category> = listOf(
    Category(id = 1, emoji = "🏠", isIncome = false, name = "Аренда квартиры"),
    Category(id = 2, emoji = "👗", isIncome = false, name = "Одежда"),
    Category(id = 3, emoji = "🐶", isIncome = false, name = "На собачку"),
    Category(id = 4, emoji = "🐶", isIncome = false, name = "На собачку"),
    Category(
        id = 5,
        emoji = "рк",
        isIncome = false,
        name = "Ремонт квартиры"
    ),
    Category(id = 6, emoji = "🍭", isIncome = false, name = "Продукты"),
    Category(id = 7, emoji = "🤸‍♂️", isIncome = false, name = "Спортзал"),
    Category(id = 8, emoji = "💊", isIncome = false, name = "Медицина")
)
