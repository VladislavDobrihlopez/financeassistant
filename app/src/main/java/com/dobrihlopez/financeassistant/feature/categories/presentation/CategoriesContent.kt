package com.dobrihlopez.financeassistant.feature.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.core_ui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesStore.CategoriesScreenState
import com.dobrihlopez.financeassistant.feature.categories.presentation.composable.CategoriesItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesContent(
    state: CategoriesScreenState,
    onSearchBarTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
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
            .height(56.dp)
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
                contentDescription = null
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
            onSearchClick = {},
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
            onSearchClick = {},
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
            onSearchClick = {},
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
            onSearchClick = {},
            onSearchBarTextChange = {}
        )
    }
}

