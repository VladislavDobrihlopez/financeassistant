package com.dobrihlopez.financeassistant.core_ui.composable

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseComponent
import kotlin.reflect.KClass

sealed class NavigationItem(
    val route: String,
    val icon: Int,
    val label: String
) {
    data object Expenses : NavigationItem("expenses", R.drawable.ic_expenses, "Расходы")
    data object Incomes : NavigationItem("incomes", R.drawable.ic_incomes, "Доходы")
    data object Accounts : NavigationItem("accounts", R.drawable.ic_accounts, "Счёт")
    data object Categories : NavigationItem("categories", R.drawable.ic_articles, "Статьи")
    data object Settings : NavigationItem("settings", R.drawable.ic_settings, "Настройки")

    companion object {
        val items = listOf(Expenses, Incomes, Accounts, Categories, Settings)
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: RootComponent.Child,
    onNavigate: (NavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        NavigationItem.items.forEach { item ->
            val selected = when (currentRoute) {
                is RootComponent.Child.Expenses -> item is NavigationItem.Expenses
                is RootComponent.Child.Income -> item is NavigationItem.Incomes
                is RootComponent.Child.Accounts -> item is NavigationItem.Accounts
                is RootComponent.Child.Category -> item is NavigationItem.Categories
                is RootComponent.Child.Settings -> item is NavigationItem.Settings
            }
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
//
//@Preview(showBackground = true, name = "Bottom Navigation Light")
//@Composable
//private fun PreviewBottomNavigationLight() {
//    FinanceAssistantTheme(darkTheme = false) {
//        BottomNavigationBar(
//            currentRoute = RootComponent.Child.Expenses(
//                ExpenseComponent.DefaultExpenseComponent(
//                    componentContext = defaultComponentContext(),
//                    storeFactory = DefaultStoreFactory()
//                )
//            ),
//            onNavigate = {}
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Bottom Navigation Dark")
//@Composable
//private fun PreviewBottomNavigationDark() {
//    FinanceAssistantTheme(darkTheme = true) {
//        BottomNavigationBar(
//            currentRoute = RootComponent.Child.Expenses(
//                ExpenseComponent.DefaultExpenseComponent(
//                    componentContext = defaultComponentContext(),
//                    storeFactory = DefaultStoreFactory()
//                )
//            ),
//            onNavigate = {}
//        )
//    }
//}