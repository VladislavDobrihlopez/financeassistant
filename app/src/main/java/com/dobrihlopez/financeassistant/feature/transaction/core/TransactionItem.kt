package com.dobrihlopez.financeassistant.feature.transaction.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core_ui.composable.BasicListItem
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    showTime: Boolean = false,
) {
    BasicListItem(
        modifier = Modifier.height(70.dp),
        content = transaction.category.name,
        subContent = transaction.comment,
        value = transaction.amount,
        valueSubtitle = if (showTime) {
            OffsetDateTime.parse(transaction.updatedAt).format(DateTimeFormatter.ofPattern("HH:mm"))
        } else null,
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
        trailingContent = {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_head), contentDescription = null)
        },
        onClick = onClick
    )
}
