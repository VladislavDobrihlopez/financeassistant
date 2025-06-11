package com.dobrihlopez.financeassistant.feature.transaction_core.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core_ui.composable.BasicListItemWithTrailingIcon

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
) {
    BasicListItemWithTrailingIcon(
        modifier = Modifier.height(70.dp),
        content = transaction.category.name,
        value = transaction.amount,
        leadingContent = transaction.category.emoji?.run {
            {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable(onClick = onClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = transaction.category.emoji,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        trailingIcon = Icons.AutoMirrored.Default.KeyboardArrowRight,
        onClick = onClick
    )
}
