package com.dobrihlopez.financeassistant.feature.transaction.expenses.data

import com.dobrihlopez.financeassistant.core.network.ExpenseDto
import com.dobrihlopez.financeassistant.core.network.ExpenseHistoryDto
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.Expense
import com.dobrihlopez.financeassistant.feature.transaction.expenses.domain.ExpenseHistory

fun ExpenseDto.toDomain(): Expense = Expense(
    id = id,
    amount = amount,
    category = category,
    date = date
)

fun ExpenseHistoryDto.toDomain(): ExpenseHistory = ExpenseHistory(
    id = id,
    amount = amount,
    date = date
) 