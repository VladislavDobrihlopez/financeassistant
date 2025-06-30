package com.dobrihlopez.financeassistant.core.usecase.account

import com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository
import javax.inject.Inject

class GetFirstAccountUseCase
    @Inject
    constructor(
        private val repository: AccountsRepository,
    ) {
        suspend operator fun invoke(): UserAccountDetailed? = repository.getAccounts().firstOrNull()
    }
