package com.dobrihlopez.financeassistant.feature.transaction.history.domain

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction

interface GetSortedTransactionsUsecase {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): List<Transaction>
}
