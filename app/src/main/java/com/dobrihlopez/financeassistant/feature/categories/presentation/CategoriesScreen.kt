package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CategoriesScreen(componentContext: CategoriesComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    CategoriesContent(state = screenState, onSearchBarTextChange = componentContext::onSearchBarTextChange, onSearchClick = {})
}
