package com.dobrihlopez.financeassistant.feature.transaction.income.domain

interface IncomeRepository {
    suspend fun getIncomeList(): List<Income>
    suspend fun getIncomeHistory(period: String? = null): List<IncomeHistory>
} 