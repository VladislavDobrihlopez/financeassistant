package com.dobrihlopez.financeassistant.core.useraccount

data class UserAccountDetailed(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val expenseStats: List<ExpenseStatistics>,
    val id: Int,
    val incomeStatistics: List<IncomeStatistics>,
    val name: String,
    val updatedAt: String
)
