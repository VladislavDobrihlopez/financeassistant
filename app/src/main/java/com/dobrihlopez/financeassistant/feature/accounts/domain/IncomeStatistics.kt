package com.dobrihlopez.financeassistant.feature.accounts.domain

import kotlinx.serialization.Serializable

@Serializable
data class IncomeStatistics(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String
)