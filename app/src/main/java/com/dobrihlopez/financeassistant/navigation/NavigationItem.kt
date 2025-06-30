package com.dobrihlopez.financeassistant.navigation

import androidx.annotation.StringRes
import com.dobrihlopez.financeassistant.R

sealed class NavigationItem(
    val route: String,
    val icon: Int,
    @StringRes val labelResId: Int,
) {
    data object Expenses :
        NavigationItem("expenses", R.drawable.ic_expenses, R.string.navigation_item_expenses)

    data object Incomes : NavigationItem(
        "incomes",
        R.drawable.ic_incomes,
        R.string.navigation_item_incomes,
    )

    data object Accounts : NavigationItem(
        "accounts",
        R.drawable.ic_accounts,
        R.string.navigation_item_account,
    )

    data object Categories : NavigationItem(
        "categories",
        R.drawable.ic_articles,
        R.string.navigation_item_categories,
    )

    data object Settings : NavigationItem(
        "settings",
        R.drawable.ic_settings,
        R.string.navigation_item_settings,
    )

    companion object {
        val items = listOf(Expenses, Incomes, Accounts, Categories, Settings)
    }
}
