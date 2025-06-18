package com.dobrihlopez.financeassistant.core.domain.income

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