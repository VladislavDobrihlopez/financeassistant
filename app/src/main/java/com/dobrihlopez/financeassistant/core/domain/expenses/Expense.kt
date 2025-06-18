package com.dobrihlopez.financeassistant.core.domain.expenses

data class Expense(
    val id: Long,
    val amount: Double,
    val category: String,
    val date: String
)

data class ExpenseHistory(
    val id: Long,
    val amount: Double,
    val date: String
) 