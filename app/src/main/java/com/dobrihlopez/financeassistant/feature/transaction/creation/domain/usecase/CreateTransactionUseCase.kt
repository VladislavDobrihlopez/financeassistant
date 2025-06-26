package com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase

import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.TransactionRepository
import java.time.LocalDateTime
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    suspend operator fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: LocalDateTime,
        comment: String,
    ) {
        repository.createTransaction(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
        )
    }
}
