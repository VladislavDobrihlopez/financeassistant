package com.dobrihlopez.financeassistant.feature.accounts.domain.usecase

import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed
import javax.inject.Inject

class GetFirstAccountUseCase
    @Inject
    constructor(
        private val repository: AccountsRepository,
    ) {
        suspend operator fun invoke(): UserAccountDetailed? = repository.getAccounts().firstOrNull()
    }
