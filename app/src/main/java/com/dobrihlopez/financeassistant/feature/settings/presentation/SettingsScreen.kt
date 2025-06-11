package com.dobrihlopez.financeassistant.feature.settings.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingScreen(componentContext: SettingsComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    SettingScreenContent(state = screenState, onOptionClicked = {})
}
