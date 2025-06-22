package com.dobrihlopez.financeassistant.feature.categories.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.ErrorSnackbarHost
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.categories.domain.model.Category
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesScreenState
import com.dobrihlopez.financeassistant.feature.categories.presentation.composable.CategoriesItem
import com.dobrihlopez.financeassistant.feature.categories.presentation.composable.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesContent(
    state: CategoriesScreenState,
    onSearchBarTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onRetry: () -> Unit = {},
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.categories_topbar_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            },
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
        )
    }, snackbarHost = {
        if (state is CategoriesScreenState.Failed) {
            ErrorSnackbarHost(
                errorResId = state.errorResId,
                onRetry = onRetry,
            )
        }
    }) { values ->
        when (state) {
            is CategoriesScreenState.Failed -> {}
            CategoriesScreenState.Loading -> LoadingProgressBar()
            is CategoriesScreenState.Succeeded -> {
                Column(modifier = Modifier.padding(values)) {
                    SearchBar(
                        searchText = state.searchText,
                        onTextChange = onSearchBarTextChange,
                        onSearchClick = onSearchClick,
                    )
                    HorizontalDivider()
                    LazyColumn {
                        items(items = state.categories, key = { it.id }) { category ->
                            CategoriesItem(category = category)
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Light Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true,
)
@Composable
private fun PreviewLightRussian() {
    FinanceAssistantTheme(darkTheme = false) {
        CategoriesContent(
            state =
                CategoriesScreenState.Succeeded(
                    searchText = "Найти статью",
                    categories = provideCategories(),
                ),
            onSearchClick = {},
            onSearchBarTextChange = {},
        )
    }
}

@Preview(
    name = "Dark Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true,
)
@Composable
private fun PreviewDarkRussian() {
    FinanceAssistantTheme(darkTheme = true) {
        CategoriesContent(
            state =
                CategoriesScreenState.Succeeded(
                    searchText = "Найти статью",
                    categories = provideCategories(),
                ),
            onSearchClick = {},
            onSearchBarTextChange = {},
        )
    }
}

@Preview(
    name = "Light Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true,
)
@Composable
private fun PreviewLightEnglish() {
    FinanceAssistantTheme(darkTheme = false) {
        CategoriesContent(
            state =
                CategoriesScreenState.Succeeded(
                    searchText = "Найти статью",
                    categories = provideCategories(),
                ),
            onSearchClick = {},
            onSearchBarTextChange = {},
        )
    }
}

@Preview(
    name = "Dark Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true,
)
@Composable
private fun PreviewDarkEnglish() {
    FinanceAssistantTheme(darkTheme = true) {
        CategoriesContent(
            state =
                CategoriesScreenState.Succeeded(
                    searchText = "Найти статью",
                    categories = provideCategories(),
                ),
            onSearchClick = {},
            onSearchBarTextChange = {},
        )
    }
}

// mock data
fun provideCategories(): List<Category> =
    listOf(
        Category(id = 1, emoji = "🏠", isIncome = false, name = "Аренда квартиры"),
        Category(id = 2, emoji = "👗", isIncome = false, name = "Одежда"),
        Category(id = 3, emoji = "🐶", isIncome = false, name = "На собачку"),
        Category(id = 4, emoji = "🐶", isIncome = false, name = "На собачку"),
        Category(
            id = 5,
            emoji = "рк",
            isIncome = false,
            name = "Ремонт квартиры",
        ),
        Category(id = 6, emoji = "🍭", isIncome = false, name = "Продукты"),
        Category(id = 7, emoji = "🤸‍♂️", isIncome = false, name = "Спортзал"),
        Category(id = 8, emoji = "💊", isIncome = false, name = "Медицина"),
    )
