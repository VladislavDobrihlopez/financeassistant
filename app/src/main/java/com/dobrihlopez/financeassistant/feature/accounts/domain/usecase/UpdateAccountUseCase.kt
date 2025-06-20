package com.dobrihlopez.financeassistant.feature.accounts.domain.usecase

import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(id: Int, name: String, balance: String, currency: String): com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed =
        repository.updateAccount(id, name, balance, currency)
} 