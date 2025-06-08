package com.dobrihlopez.financeassistant.feature.accounts.domain

data class ExpenseStatistics(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
)
