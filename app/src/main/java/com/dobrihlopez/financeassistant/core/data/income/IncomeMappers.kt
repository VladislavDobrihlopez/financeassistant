package com.dobrihlopez.financeassistant.core.data.income

import com.dobrihlopez.financeassistant.core.domain.income.Income
import com.dobrihlopez.financeassistant.core.domain.income.IncomeHistory
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