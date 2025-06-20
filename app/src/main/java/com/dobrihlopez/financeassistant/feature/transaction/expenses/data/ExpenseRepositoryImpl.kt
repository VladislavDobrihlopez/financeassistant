package com.dobrihlopez.financeassistant.feature.transaction.expenses.data

import com.dobrihlopez.financeassistant.core.network.ExpenseApi
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.Expense
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseHistory
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseRepository
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val api: ExpenseApi
) : ExpenseRepository {
    override suspend fun getExpenseList(): List<Expense> =
        api.getExpenseList().map { it.toDomain() }

    override suspend fun getExpenseHistory(period: String?): List<ExpenseHistory> =
        api.getExpenseHistory(period).map { it.toDomain() }
} 