package com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase

import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase
    @Inject
    constructor(
        private val repository: TransactionRepository,
    ) {
        suspend operator fun invoke(transactionId: Int) {
            repository.deleteTransaction(transactionId)
        }
    }
