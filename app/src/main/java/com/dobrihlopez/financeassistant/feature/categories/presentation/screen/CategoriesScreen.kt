package com.dobrihlopez.financeassistant.feature.categories.presentation.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesComponent

@Composable
fun CategoriesScreen(componentContext: CategoriesComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    CategoriesContent(
        state = screenState,
        onSearchBarTextChange = componentContext::onSearchBarTextChange,
        onSearchClick = componentContext::onSearchClick,
    )
}
