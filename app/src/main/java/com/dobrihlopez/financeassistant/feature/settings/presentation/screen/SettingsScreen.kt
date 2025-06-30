package com.dobrihlopez.financeassistant.feature.settings.presentation.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsComponent

@Composable
fun SettingScreen(componentContext: SettingsComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    SettingScreenContent(state = screenState, onOptionClicked = {})
}
