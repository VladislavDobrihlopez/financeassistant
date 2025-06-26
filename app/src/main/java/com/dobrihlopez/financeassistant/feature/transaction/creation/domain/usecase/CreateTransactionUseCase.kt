package com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase

import java.time.LocalDateTime
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor() {
    suspend operator fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: LocalDateTime,
        comment: String,
    ) {

    }
}