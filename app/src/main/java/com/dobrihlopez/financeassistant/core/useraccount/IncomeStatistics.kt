package com.dobrihlopez.financeassistant.core.useraccount

data class IncomeStatistics(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String
)