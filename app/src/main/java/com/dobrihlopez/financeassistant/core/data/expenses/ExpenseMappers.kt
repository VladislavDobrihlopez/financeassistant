package com.dobrihlopez.financeassistant.core.data.expenses

import com.dobrihlopez.financeassistant.core.domain.expenses.Expense
import com.dobrihlopez.financeassistant.core.domain.expenses.ExpenseHistory
import com.dobrihlopez.financeassistant.core.network.ExpenseDto
import com.dobrihlopez.financeassistant.core.network.ExpenseHistoryDto

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