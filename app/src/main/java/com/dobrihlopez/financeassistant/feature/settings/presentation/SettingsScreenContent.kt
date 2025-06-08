package com.dobrihlopez.financeassistant.feature.settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.settings.domain.AppSettingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.settings_topbar_title),
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
        val context = LocalContext.current

        // TODO move this logic out into component/viewmodel
        val provider = remember {
            AndroidSettingsProvider(context)
        }

        val items = remember(provider) {
            AppSettingItem.all.map { it to provider.provide(it) }
        }

        LazyColumn(modifier = Modifier.padding(values)) {

            itemsIndexed(
                items = items,
                key = { _, (section, _) -> section.id }
            ) { idx, (section, name) ->
                SettingItem(section = section, sectionName = name) {
                    // TODO delegate to component/viewmodel
                }

                if (idx >= 0) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showSystemUi = true, locale = "ru")
@Composable
private fun PreviewSettingScreenContent_Light_Ru() {
    FinanceAssistantTheme {
        SettingScreenContent()
    }
}

@Preview(showSystemUi = true, locale = "ru")
@Composable
private fun PreviewSettingScreenContent_Dark_Ru() {
    FinanceAssistantTheme(darkTheme = true) {
        SettingScreenContent()
    }
}

@Preview(showSystemUi = true, locale = "eng")
@Composable
private fun PreviewSettingScreenContent_Light_Eng() {
    FinanceAssistantTheme {
        SettingScreenContent()
    }
}

@Preview(showSystemUi = true, locale = "eng")
@Composable
private fun PreviewSettingScreenContent_Dark_Eng() {
    FinanceAssistantTheme(darkTheme = true) {
        SettingScreenContent()
    }
}