package com.dobrihlopez.financeassistant.feature.transaction.core_data.mapper

import com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed
import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionDto

fun TransactionDto.toDomain(): Transaction =
    Transaction(
        id = id,
        account =
            UserAccountDetailed(
                id = account.id,
                name = account.name ?: "",
                balance = account.balance ?: "0.0",
                currency = account.currency ?: "",
                expenseStats = emptyList(),
                incomeStatistics = emptyList(),
                createdAt = createdAt,
                updatedAt = updatedAt,
            ),
        amount = amount,
        category =
            Category(
                id = category.id,
                name = category.name ?: "",
                emoji = category.emoji ?: "",
                isIncome = category.isIncome,
            ),
        comment = comment ?: "",
        createdAt = createdAt,
        transactionDate = transactionDate,
        updatedAt = updatedAt,
    )
