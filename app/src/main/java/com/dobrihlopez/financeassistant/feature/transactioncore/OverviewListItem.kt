package com.dobrihlopez.financeassistant.feature.transactioncore

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.coreui.composable.BasicListItem

@Composable
fun OverViewListItem(content: String, value: String) {
    BasicListItem(
        modifier = Modifier.height(50.dp),
        content = content,
        value = value,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    )
}
