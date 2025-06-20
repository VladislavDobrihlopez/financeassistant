package com.dobrihlopez.financeassistant.feature.transaction.expenses.domain

interface ExpenseRepository {
    suspend fun getExpenseList(): List<Expense>
    suspend fun getExpenseHistory(period: String? = null): List<ExpenseHistory>
} 