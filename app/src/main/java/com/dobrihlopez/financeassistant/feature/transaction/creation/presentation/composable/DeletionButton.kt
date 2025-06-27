package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.composable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dobrihlopez.financeassistant.core_ui.ui.theme.DestructiveActionColor
import com.dobrihlopez.financeassistant.core_ui.ui.theme.OnDestructiveActionColor

@Composable
fun DeletionButton(
    onClick: () -> Unit,
    content: String,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor = DestructiveActionColor,
                contentColor = OnDestructiveActionColor,
            ),
    ) {
        Text(text = content)
    }
}
