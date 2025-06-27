package com.dobrihlopez.financeassistant.feature.settings.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsStore.SettingsScreenState
import com.dobrihlopez.financeassistant.feature.settings.presentation.composable.SettingItem
import com.dobrihlopez.financeassistant.feature.settings.presentation.platform.AndroidSettingsProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    state: SettingsScreenState,
    onOptionClicked: (AppSettingItem) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.settings_topbar_title),
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
        },
    ) { paddingValues ->
        when (state) {
            is SettingsScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingProgressBar()
                }
            }
            is SettingsScreenState.Failed -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.errorResId?.let { stringResource(it) } ?: "Error",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            is SettingsScreenState.Succeeded -> {
                val context = LocalContext.current
                val provider = remember { AndroidSettingsProvider(context) }
                val items =
                    remember(provider) {
                        state.items.map { it to provider.provide(it) }
                    }

                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    itemsIndexed(
                        items = items,
                        key = { _, (section, _) -> section.id },
                    ) { idx, (section, name) ->
                        SettingItem(section = section, sectionName = name, onClick = onOptionClicked)
                        if (idx <= items.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Settings Light")
@Composable
private fun PreviewSettingScreenContent_Light() {
    FinanceAssistantTheme(darkTheme = false) {
        SettingScreenContent(
            state =
                SettingsScreenState.Succeeded(
                    items = AppSettingItem.all,
                ),
            onOptionClicked = {},
        )
    }
}

@Preview(showBackground = true, name = "Settings Dark")
@Composable
private fun PreviewSettingScreenContent_Dark() {
    FinanceAssistantTheme(darkTheme = true) {
        SettingScreenContent(
            state =
                SettingsScreenState.Succeeded(
                    items = AppSettingItem.all,
                ),
            onOptionClicked = {},
        )
    }
}

@Preview(showBackground = true, name = "Settings Loading")
@Composable
private fun PreviewSettingScreenContent_Loading() {
    FinanceAssistantTheme {
        SettingScreenContent(
            state = SettingsScreenState.Loading,
            onOptionClicked = {},
        )
    }
}

@Preview(showBackground = true, name = "Settings Error")
@Composable
private fun PreviewSettingScreenContent_Error() {
    FinanceAssistantTheme {
        SettingScreenContent(
            state = SettingsScreenState.Failed(null),
            onOptionClicked = {},
        )
    }
}
