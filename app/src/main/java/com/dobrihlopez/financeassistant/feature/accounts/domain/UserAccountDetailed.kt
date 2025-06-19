package com.dobrihlopez.financeassistant.feature.accounts.domain

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
    val updatedAt: String
)

fun com.dobrihlopez.financeassistant.feature.accounts.data.model.AccountDto.toDomain(): UserAccountDetailed =
    UserAccountDetailed(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        expenseStats = emptyList(),
        incomeStatistics = emptyList(),
    )
