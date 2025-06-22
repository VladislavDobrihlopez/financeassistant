package com.dobrihlopez.financeassistant.feature.accounts.data.repository

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.feature.accounts.data.mapper.toDomain
import com.dobrihlopez.financeassistant.feature.accounts.data.network.AccountsApiService
import com.dobrihlopez.financeassistant.feature.accounts.data.network.UpdateAccountRequest
import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val api: AccountsApiService,
    private val coroutineDispatchers: CoroutineDispatchers,
) : AccountsRepository {
    override suspend fun getAccounts(): List<UserAccountDetailed> =
        withContext(coroutineDispatchers.io) {
            api.getAccounts().map { it.toDomain() }
        }

    override suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ): UserAccountDetailed =
        withContext(coroutineDispatchers.io) {
            api.updateAccount(id, UpdateAccountRequest(name, balance, currency)).toDomain()
        }
}