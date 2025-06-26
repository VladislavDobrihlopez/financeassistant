package com.dobrihlopez.financeassistant.feature.transaction.creation.domain.usecase

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor() {
    suspend operator fun invoke(transaction: Transaction) {

    }
}