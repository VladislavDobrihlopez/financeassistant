package com.dobrihlopez.financeassistant.feature.transaction.income.data

import com.dobrihlopez.financeassistant.feature.transaction.income.domain.Income
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeHistory
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeRepository
import com.dobrihlopez.financeassistant.core.network.IncomeApi
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val api: IncomeApi
) : IncomeRepository {
    override suspend fun getIncomeList(): List<Income> =
        api.getIncomeList().map { it.toDomain() }

    override suspend fun getIncomeHistory(period: String?): List<IncomeHistory> =
        api.getIncomeHistory(period).map { it.toDomain() }
} 