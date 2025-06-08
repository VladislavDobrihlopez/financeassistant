package com.dobrihlopez.financeassistant.coreui.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 20.dp,
    val extraLarge: Dp = 32.dp,
)

private val LocalSpacing
    @[Composable ReadOnlyComposable]
    get() = staticCompositionLocalOf { Spacing() } // child composable mustn't change any spacings

val MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current
