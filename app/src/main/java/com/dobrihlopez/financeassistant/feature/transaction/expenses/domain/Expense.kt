package com.dobrihlopez.financeassistant.feature.transaction.expenses.domain

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