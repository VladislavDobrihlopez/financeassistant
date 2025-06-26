package com.dobrihlopez.financeassistant.feature.accounts.data.mapper

import com.dobrihlopez.financeassistant.core_remote.network.dto.AccountDto
import com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed

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
