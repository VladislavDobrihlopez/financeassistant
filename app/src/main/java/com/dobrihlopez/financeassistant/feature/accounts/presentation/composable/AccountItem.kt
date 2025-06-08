package com.dobrihlopez.financeassistant.feature.accounts.presentation.composable

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.coreui.composable.BasicListItemWithTrailingIcon
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountActionItem

@Composable
fun AccountItem(accountActionItem: AccountActionItem, onClick: () -> Unit) {
    val context = LocalContext.current
    BasicListItemWithTrailingIcon(
        modifier = Modifier.height(70.dp),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        content = context.getString(accountActionItem.title),
        valueSubtitle = accountActionItem.value + " " + accountActionItem.currency,
        leadingContent = {
            if (accountActionItem.emoji != null) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable(onClick = onClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = accountActionItem.emoji,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        trailingIcon = Icons.AutoMirrored.Default.KeyboardArrowRight,
    )
}
