package com.dobrihlopez.financeassistant.feature.accounts.presentation

import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed

sealed class AccountScreenState {
    data object Loading : AccountScreenState()
    data class Failed(val message: String? = null) : AccountScreenState()
    data class Succeeded(
        val account: UserAccountDetailed
    ) : AccountScreenState()
}
