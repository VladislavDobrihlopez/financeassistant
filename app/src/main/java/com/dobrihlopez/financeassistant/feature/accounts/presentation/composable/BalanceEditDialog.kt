package com.dobrihlopez.financeassistant.feature.accounts.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceEditDialog(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    initialBalance: String,
    onDismiss: () -> Unit,
    onBalanceChanged: (String) -> Unit,
) {
    var balance by remember { mutableStateOf(initialBalance) }
    val isValid by remember(balance) {
        derivedStateOf {
            balance.matches(Regex("^\\d+(\\.\\d{0,2})?$")) && balance.isNotBlank()
        }
    }
    val errorColor = MaterialTheme.colorScheme.error
    val spacing = MaterialTheme.spacing

    ModalBottomSheet(modifier = modifier, sheetState = sheetState, onDismissRequest = onDismiss) {
        OutlinedTextField(
            value = balance,
            onValueChange = { balance = it.replace(',', '.') },
            label = { Text(stringResource(R.string.accounts_input_field_balance)) },
            isError = !isValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        )
        if (!isValid) {
            Text(
                text = stringResource(R.string.accounts_balance_validation_helper),
                color = errorColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = spacing.medium, top = spacing.tiny)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = errorColor
                )
            ) {
                Text(stringResource(R.string.cancel), color = errorColor)
            }
            Spacer(Modifier.width(spacing.small))
            Button(
                onClick = { onBalanceChanged(balance) },
                enabled = isValid
            ) {
                Text(stringResource(R.string.okay))
            }
        }
    }
}
