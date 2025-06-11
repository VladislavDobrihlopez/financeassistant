package com.dobrihlopez.financeassistant.core_ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2AE881),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD4FAE6),
    onPrimaryContainer = Color(0xFF004D35),
    background = Color(0xFFFEF7FF),
    onBackground = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F),
    surface = Color(0xFFF3EDF7),
    onSurface = Color(0xFF1D1B20),
    surfaceContainerHigh = Color(0xFFECE6F0),
    secondary = Color(0xFFB8B3C4),
    onSecondary = Color(0xFF2F2B3A),
    error = DestructiveActionColor,
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2AE881),
    onPrimary = Color(0xFF00391F),
    primaryContainer = Color(0xFF005233),
    onPrimaryContainer = Color(0xFFD4FAE6),
    background = Color(0xFF1D1B20),
    onBackground = Color(0xFFE6E1E5),
    onSurfaceVariant = Color(0xFF49454F),
    surface = Color(0xFF1D1B20),
    surfaceContainerHigh = Color(0xFFECE6F0),
    onSurface = Color(0xFFE6E1E5),
    secondary = Color(0xFFCCC6DC),
    onSecondary = Color(0xFF3B3948),
    error = DestructiveActionColor,
    onError = Color(0xFF370001),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

@Composable
fun FinanceAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}