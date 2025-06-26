package com.dobrihlopez.financeassistant.feature.accounts.presentation.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core.validation.BalanceValidator
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.modalsheet.InputNumberBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceEditDialog(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    initialBalance: String,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit,
) {
    var balance by rememberSaveable { mutableStateOf(initialBalance) }
    val isValid by remember(balance) {
        derivedStateOf {
            BalanceValidator.validate(balance)
        }
    }

    InputNumberBottomSheet(
        inputValue = balance,
        isEnteredValueValid = isValid,
        sheetState = sheetState,
        label = stringResource(R.string.accounts_input_field_balance),
        errorText = stringResource(R.string.accounts_balance_validation_helper),
        onValueChanged = {
            balance = it.replace(',', '.')
        },
        onDismissRequest = onDismiss,
        onDone = onDone
    )
}
