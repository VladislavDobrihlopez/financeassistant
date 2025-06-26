package com.dobrihlopez.financeassistant.core.model.account

import kotlinx.serialization.Serializable

@Serializable
data class UserAccountDetailed(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val expenseStats: List<ExpenseStatistics>,
    val id: Int,
    val incomeStatistics: List<IncomeStatistics>,
    val name: String,
    val updatedAt: String,
)
