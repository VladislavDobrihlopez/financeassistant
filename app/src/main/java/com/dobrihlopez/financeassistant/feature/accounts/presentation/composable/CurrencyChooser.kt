package com.dobrihlopez.financeassistant.feature.accounts.presentation.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.dobrihlopez.financeassistant.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChooser(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onCurrencySelected: (Currency) -> Unit
) {
    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
        CurrencyItem(currency = Currency.Ruble, onClick = { onCurrencySelected(Currency.Ruble) })
        CurrencyItem(currency = Currency.Usd, onClick = { onCurrencySelected(Currency.Usd) })
        CurrencyItem(currency = Currency.Euro, onClick = { onCurrencySelected(Currency.Euro) })
    }
}

@Composable
private fun CurrencyItem(currency: Currency, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Icon(imageVector = ImageVector.vectorResource(currency.iconResId), contentDescription = currency.symbol.toString())

    }
}

sealed class Currency(@DrawableRes val iconResId: Int, val symbol: Char) {
    data object Euro : Currency(iconResId = R.drawable.ic_euro, symbol = '€')
    data object Usd: Currency(iconResId = R.drawable.ic_dollar, symbol = '$')
    data object Ruble: Currency(iconResId = R.drawable.ic_ruble, symbol = '₽')
}
