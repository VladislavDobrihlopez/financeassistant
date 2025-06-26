package com.dobrihlopez.financeassistant.coreui.composable.item

import androidx.compose.foundation.background
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
import com.dobrihlopez.financeassistant.core.model.category.Category

@Composable
fun CategoriesItem(
    category: Category,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val trailingContent = if (isSelected) {
        @Composable {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_apply),
                contentDescription = ""
            )
        }
    } else null

    BasicListItem(
        modifier = modifier.height(70.dp),
        content = category.name,
        onClick = onClick,
        leadingContent =
            category.emoji?.run {
                {
                    Box(
                        modifier =
                            Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = category.emoji,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            },
        trailingContent = trailingContent,
    )
}
