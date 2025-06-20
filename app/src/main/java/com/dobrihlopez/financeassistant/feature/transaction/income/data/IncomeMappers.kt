package com.dobrihlopez.financeassistant.feature.transaction.income.data

import com.dobrihlopez.financeassistant.feature.transaction.income.domain.Income
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeHistory
import com.dobrihlopez.financeassistant.core.network.IncomeDto
import com.dobrihlopez.financeassistant.core.network.IncomeHistoryDto

fun IncomeDto.toDomain(): Income = Income(
    id = id,
    amount = amount,
    category = category,
    date = date
)

fun IncomeHistoryDto.toDomain(): IncomeHistory = IncomeHistory(
    id = id,
    amount = amount,
    date = date
) 