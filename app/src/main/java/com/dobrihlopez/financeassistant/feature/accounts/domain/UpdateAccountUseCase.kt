package com.dobrihlopez.financeassistant.feature.accounts.domain

import com.dobrihlopez.financeassistant.feature.accounts.data.AccountsRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(id: Int, name: String, balance: String, currency: String): UserAccountDetailed =
        repository.updateAccount(id, name, balance, currency)
} 