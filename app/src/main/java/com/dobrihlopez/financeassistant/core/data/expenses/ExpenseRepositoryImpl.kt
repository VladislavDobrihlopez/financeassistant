package com.dobrihlopez.financeassistant.core.data.expenses

import com.dobrihlopez.financeassistant.core.domain.expenses.Expense
import com.dobrihlopez.financeassistant.core.domain.expenses.ExpenseHistory
import com.dobrihlopez.financeassistant.core.domain.expenses.ExpenseRepository
import com.dobrihlopez.financeassistant.core.network.ExpenseApi
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val api: ExpenseApi
) : ExpenseRepository {
    override suspend fun getExpenseList(): List<Expense> =
        api.getExpenseList().map { it.toDomain() }

    override suspend fun getExpenseHistory(period: String?): List<ExpenseHistory> =
        api.getExpenseHistory(period).map { it.toDomain() }
} 