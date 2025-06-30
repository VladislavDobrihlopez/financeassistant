package com.dobrihlopez.financeassistant.feature.accounts.presentation.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dobrihlopez.financeassistant.R

sealed class Currency(
    @DrawableRes val iconResId: Int,
    val symbol: Char,
    @StringRes val textResId: Int,
) {
    data object Euro : Currency(
        iconResId = R.drawable.ic_euro,
        symbol = '€',
        textResId = R.string.accounts_pattern_euro,
    )

    data object Usd : Currency(
        iconResId = R.drawable.ic_dollar,
        symbol = '$',
        textResId = R.string.accounts_pattern_dollar,
    )

    data object Ruble : Currency(
        iconResId = R.drawable.ic_ruble,
        symbol = '₽',
        textResId = R.string.accounts_pattern_russian_rubble,
    )
}
