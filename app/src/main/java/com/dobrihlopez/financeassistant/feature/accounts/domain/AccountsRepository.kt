package com.dobrihlopez.financeassistant.feature.accounts.domain

import com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed

interface AccountsRepository {
    suspend fun getAccounts(): List<UserAccountDetailed>

    suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ): UserAccountDetailed
}
