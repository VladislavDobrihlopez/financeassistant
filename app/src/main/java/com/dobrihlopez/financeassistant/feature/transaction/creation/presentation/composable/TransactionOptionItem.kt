package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.composable

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core.default
import com.dobrihlopez.financeassistant.coreui.composable.BasicListItem

@Composable
fun TransactionOptionItem(
    content: String,
    value: String,
    onClick: () -> Unit,
    hasChooseOption: Boolean = false,
) {
    val (currentContent, currentValue) = if (content.trim().isEmpty()) {
        value to String.default
    } else {
        content to value
    }

    BasicListItem(
        modifier = Modifier.height(70.dp),
        content = currentContent,
        value = currentValue,
        onClick = onClick,
        trailingContent = {
            if (hasChooseOption) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_head),
                    contentDescription = null
                )
            }
        })
}
