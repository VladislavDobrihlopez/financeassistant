package com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.modalsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentaryInputBottomSheet(
    initialText: String,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit,
    errorColor: Color = MaterialTheme.colorScheme.error,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.transaction_input_field_commentary)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            maxLines = 5
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onDismiss,
                colors =
                    ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = errorColor,
                    )
            ) {
                Text(text = stringResource(R.string.cancel), color = errorColor)
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            Button(onClick = { onDone(text) }) {
                Text(stringResource(R.string.okay))
            }
        }
    }
}