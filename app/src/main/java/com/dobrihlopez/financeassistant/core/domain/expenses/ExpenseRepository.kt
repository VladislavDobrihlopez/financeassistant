package com.dobrihlopez.financeassistant.core.domain.expenses

interface ExpenseRepository {
    suspend fun getExpenseList(): List<Expense>
    suspend fun getExpenseHistory(period: String? = null): List<ExpenseHistory>
} 