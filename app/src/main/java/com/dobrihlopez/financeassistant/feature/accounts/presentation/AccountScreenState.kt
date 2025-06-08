package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.annotation.StringRes
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed

sealed class AccountScreenState {
    data object Loading : AccountScreenState()
    data class Failed(@StringRes val resId: Int? = null) : AccountScreenState()
    data class Succeeded(
        val account: UserAccountDetailed,
    ) : AccountScreenState()
}
