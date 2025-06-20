package com.dobrihlopez.financeassistant.feature.transaction.expenses.data

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.network.ExpenseApi
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.Expense
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseHistory
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val api: ExpenseApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ExpenseRepository {
    override suspend fun getExpenseList(): List<Expense> =
        withContext(coroutineDispatchers.io) {
            api.getExpenseList().map { it.toDomain() }
        }

    override suspend fun getExpenseHistory(period: String?): List<ExpenseHistory> =
        withContext(coroutineDispatchers.io) {
            api.getExpenseHistory(period).map { it.toDomain() }
        }
} 