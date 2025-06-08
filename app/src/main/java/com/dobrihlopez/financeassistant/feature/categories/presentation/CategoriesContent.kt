package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import com.dobrihlopez.financeassistant.feature.categories.presentation.composable.CategoriesItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesContent(
    state: CategoriesScreenState,
    onSearchBarTextChange: (String) -> Unit,
    onSearchBarClick: () -> Unit,
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            )
        )
    }) { values ->

        when (state) {
            is CategoriesScreenState.Failed -> TODO()
            CategoriesScreenState.Loading -> LoadingProgressBar()
            is CategoriesScreenState.Succeeded -> {
                LazyColumn(modifier = Modifier
                    .padding(values)
                    .fillMaxWidth()) {
                    item {
                        SearchBar(
                            searchText = state.searchText,
                            onTextChange = onSearchBarTextChange,
                            onSearchClick = onSearchBarClick,
                        )
                        HorizontalDivider()
                    }
                    items(items = state.categories, key = { it.id }) { category ->
                        CategoriesItem(category = category)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(MaterialTheme.spacing.tiny),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(start = MaterialTheme.spacing.smallPlus),
            value = searchText,
            onValueChange = onTextChange,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClick() }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchText.isEmpty()) {
                        Text(
                            text = stringResource(R.string.categories_searchbar_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
        IconButton(
            modifier = Modifier,
            onClick = onSearchClick
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Preview(
    name = "Light Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true
)
@Composable
private fun PreviewLightRussian() {
    FinanceAssistantTheme(darkTheme = false) {
        CategoriesContent(
            state = CategoriesScreenState.Succeeded(
                searchText = "Найти статью", categories = provideCategories()
            ),
            onSearchBarClick = {},
            onSearchBarTextChange = {}
        )
    }
}

@Preview(
    name = "Dark Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true
)
@Composable
private fun PreviewDarkRussian() {
    FinanceAssistantTheme(darkTheme = true) {
        CategoriesContent(
            state = CategoriesScreenState.Succeeded(
                searchText = "Найти статью", categories = provideCategories()
            ),
            onSearchBarClick = {},
            onSearchBarTextChange = {}
        )
    }
}


@Preview(
    name = "Light Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true
)
@Composable
private fun PreviewLightEnglish() {
    FinanceAssistantTheme(darkTheme = false) {
        CategoriesContent(
            state = CategoriesScreenState.Succeeded(
                searchText = "Найти статью", categories = provideCategories()
            ),
            onSearchBarClick = {},
            onSearchBarTextChange = {}
        )
    }
}

@Preview(
    name = "Dark Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true
)
@Composable
private fun PreviewDarkEnglish() {
    FinanceAssistantTheme(darkTheme = true) {
        CategoriesContent(
            state = CategoriesScreenState.Succeeded(
                searchText = "Найти статью", categories = provideCategories()
            ),
            onSearchBarClick = {},
            onSearchBarTextChange = {}
        )
    }
}

private fun provideCategories(): List<Category> = listOf(
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
