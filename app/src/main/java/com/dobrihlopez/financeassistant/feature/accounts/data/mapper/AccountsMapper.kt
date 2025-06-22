package com.dobrihlopez.financeassistant.feature.accounts.data.mapper

import com.dobrihlopez.financeassistant.core.model.AccountDto
import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed

fun AccountDto.toDomain(): UserAccountDetailed =
    UserAccountDetailed(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        expenseStats = emptyList(),
        incomeStatistics = emptyList(),
    )
