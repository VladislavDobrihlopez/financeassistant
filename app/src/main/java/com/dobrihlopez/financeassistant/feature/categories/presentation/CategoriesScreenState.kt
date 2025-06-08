package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.annotation.StringRes
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

sealed class CategoriesScreenState {
    data object Loading: CategoriesScreenState()
    data class Failed(@StringRes val errorResId: Int? = null): CategoriesScreenState()
    data class Succeeded(
        val searchText: String,
        val categories: List<Category>
    ): CategoriesScreenState()
}
