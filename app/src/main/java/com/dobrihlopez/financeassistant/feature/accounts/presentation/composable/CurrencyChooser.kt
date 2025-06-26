package com.dobrihlopez.financeassistant.feature.accounts.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.accounts.presentation.screen.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChooser(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currencies = listOf(Currency.Ruble, Currency.Usd, Currency.Euro)
    val spacing = MaterialTheme.spacing

    ModalBottomSheet(modifier = modifier, sheetState = sheetState, onDismissRequest = onDismiss) {
        currencies.forEach { currency ->
            CurrencyItem(
                modifier =
                    Modifier
                        .height(72.dp)
                        .padding(horizontal = spacing.medium),
                currency = currency,
                onClick = { onCurrencySelected(currency) },
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(MaterialTheme.colorScheme.error)
                    .clickable(onClick = onDismiss)
                    .padding(spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = stringResource(R.string.cancel),
                tint = MaterialTheme.colorScheme.onError,
            )
            Spacer(Modifier.width(spacing.small))
            Text(
                text = stringResource(R.string.cancel),
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun CurrencyItem(
    currency: Currency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(currency.iconResId),
            contentDescription = currency.symbol.toString(),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(currency.textResId).format(currency.symbol))
    }
}
