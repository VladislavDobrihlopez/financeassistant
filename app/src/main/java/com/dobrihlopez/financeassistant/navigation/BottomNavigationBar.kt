package com.dobrihlopez.financeassistant.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.feature.RootComponent

@Composable
fun BottomNavigationBar(
    currentRoute: RootComponent.Child,
    onNavigate: (NavigationItem) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
    ) {
        NavigationItem.items.forEach { item ->
            val selected =
                when (item) {
                    NavigationItem.Expenses -> currentRoute is RootComponent.Child.Expenses
                    NavigationItem.Incomes -> currentRoute is RootComponent.Child.Income
                    NavigationItem.Accounts -> currentRoute is RootComponent.Child.Accounts
                    NavigationItem.Categories -> currentRoute is RootComponent.Child.Category
                    NavigationItem.Settings -> currentRoute is RootComponent.Child.Settings
                }
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.label,
                    )
                },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }
    }
}
