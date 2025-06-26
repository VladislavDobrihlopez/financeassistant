package com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.modalsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputNumberBottomSheet(
    inputValue: String,
    isEnteredValueValid: Boolean,
    sheetState: SheetState,
    label: String,
    errorText: String,
    onValueChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onDone: (String) -> Unit,
    errorColor: Color = MaterialTheme.colorScheme.error,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        OutlinedTextField(
            value = inputValue,
            onValueChange = onValueChanged,
            label = { Text(label) },
            isError = !isEnteredValueValid,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium),
        )
        if (!isEnteredValueValid) {
            Text(
                text = errorText,
                color = errorColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = spacing.medium, top = spacing.tiny),
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = onDismissRequest,
                colors =
                    ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = errorColor,
                    ),
            ) {
                Text(stringResource(R.string.cancel), color = errorColor)
            }
            Spacer(Modifier.width(spacing.small))
            Button(
                onClick = { onDone(inputValue) },
                enabled = isEnteredValueValid,
            ) {
                Text(stringResource(R.string.okay))
            }
        }
    }
}
