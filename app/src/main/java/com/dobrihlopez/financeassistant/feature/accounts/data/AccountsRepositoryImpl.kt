package com.dobrihlopez.financeassistant.feature.accounts.data

import com.dobrihlopez.financeassistant.feature.accounts.data.model.UpdateAccountRequest
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.accounts.domain.toDomain
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val api: AccountsApiService
) : AccountsRepository {
    override suspend fun getAccounts(): List<UserAccountDetailed> =
        api.getAccounts().map { it.toDomain() }

    override suspend fun updateAccount(id: Int, name: String, balance: String, currency: String): UserAccountDetailed =
        api.updateAccount(id, UpdateAccountRequest(name, balance, currency)).toDomain()
} 