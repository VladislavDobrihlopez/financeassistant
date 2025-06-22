package com.dobrihlopez.financeassistant.feature.transaction.history.domain.impl

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core.usecase.GetTransactionsForPeriodUseCase
import com.dobrihlopez.financeassistant.feature.transaction.history.domain.GetSortedTransactionsUsecase
import java.time.OffsetDateTime
import javax.inject.Inject

class GetSortedExpenseTransactionsUsecase
    @Inject
    constructor(
        private val getTransactionsForPeriodUseCase: GetTransactionsForPeriodUseCase,
    ) : GetSortedTransactionsUsecase {
        override suspend operator fun invoke(
            accountId: Int,
            startDate: String?,
            endDate: String?,
        ): List<Transaction> {
            return getTransactionsForPeriodUseCase(accountId, startDate, endDate)
                .filter { !it.category.isIncome }
                .sortedByDescending { OffsetDateTime.parse(it.updatedAt).toLocalDateTime() }
        }
    }
