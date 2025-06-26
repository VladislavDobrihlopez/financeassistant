package com.dobrihlopez.financeassistant.feature.transaction.creation.data.mapper

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.network.TransactionRequest

fun Transaction.toRequest(): TransactionRequest {
    return TransactionRequest(
        accountId = account.id,
        categoryId = category.id,
        amount = amount,
        comment = comment,
        transactionDate = transactionDate
    )
}