package com.dobrihlopez.financeassistant.navigation

import com.dobrihlopez.financeassistant.R

sealed class NavigationItem(
    val route: String,
    val icon: Int,
    val label: String,
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