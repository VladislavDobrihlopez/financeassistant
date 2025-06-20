package com.dobrihlopez.financeassistant.feature.transaction.income.domain

data class Income(
    val id: Long,
    val amount: Double,
    val category: String,
    val date: String
)

data class IncomeHistory(
    val id: Long,
    val amount: Double,
    val date: String
) 