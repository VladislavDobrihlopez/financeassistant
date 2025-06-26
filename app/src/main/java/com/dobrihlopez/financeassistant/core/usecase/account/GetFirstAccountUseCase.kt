package com.dobrihlopez.financeassistant.core.usecase.account

class GetFirstAccountUseCase
    @javax.inject.Inject
    constructor(
        private val repository: com.dobrihlopez.financeassistant.feature.accounts.domain.AccountsRepository,
    ) {
        suspend operator fun invoke(): com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed? = repository.getAccounts().firstOrNull()
    }