package com.dobrihlopez.financeassistant.feature.accounts.domain

import com.dobrihlopez.financeassistant.feature.accounts.data.AccountsRepository
import javax.inject.Inject

class GetFirstAccountUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(): UserAccountDetailed? = repository.getAccounts().firstOrNull()
} 