package com.dobrihlopez.financeassistant.coreui.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing

@Composable
fun BasicListItem(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    title: String,
    subtitle: String? = null,
    value: String? = null,
    valueSubtitle: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val spacing = MaterialTheme.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .animateContentSize()
            .padding(horizontal = spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (value != null || valueSubtitle != null) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = if (trailingContent != null) 8.dp else 0.dp)
            ) {
                value?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End
                    )
                }
                valueSubtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        trailingContent?.invoke()
    }
}


@Composable
fun BasicListItemWithTrailingIcon(
    title: String,
    trailingIcon: ImageVector,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    subtitle: String? = null,
    value: String? = null,
    valueSubtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    BasicListItem(
        modifier = modifier,
        leadingIcon = leadingIcon,
        title = title,
        subtitle = subtitle,
        value = value,
        valueSubtitle = valueSubtitle,
        trailingContent = {
            IconButton(onClick = { onClick?.invoke() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = trailingIcon,
                    contentDescription = null
                )
            }
        },
        onClick = onClick
    )
}

@Preview(showBackground = true, name = "Simple Item")
@Composable
fun BasicListItemPreview_Simple() {
    FinanceAssistantTheme {
        BasicListItem(title = "Простой айтем")
    }
}

@Preview(showBackground = true, name = "With Leading Icon and Trailing Arrow")
@Composable
private fun BasicListItemPreview_WithIcons() {
    FinanceAssistantTheme {
        BasicListItem(
            title = "С иконкой и стрелкой",
            leadingIcon = Icons.Default.Notifications,
            trailingContent = {
                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        )
    }
}

@Preview(showBackground = true, name = "With Subtitle")
@Composable
private fun BasicListItemPreview_WithSubtitle() {
    FinanceAssistantTheme {
        BasicListItem(
            title = "С сабтайтлом",
            subtitle = "Подзаголовок для пояснения"
        )
    }
}

@Preview(showBackground = true, name = "With Value on Right")
@Composable
private fun BasicListItemPreview_WithValue() {
    FinanceAssistantTheme {
        BasicListItem(
            title = "С значением справа",
            value = "Русский"
        )
    }
}

@Preview(showBackground = true, name = "With Value and Subtitle + Trailing Icon")
@Composable
private fun BasicListItemPreview_WithValueAndSubtitleAndTrailingIcon() {
    FinanceAssistantTheme {
        BasicListItem(
            title = "С value + сабтайтлами",
            subtitle = "Подпись",
            value = "Активна",
            valueSubtitle = "Автообновление",
            trailingContent = {
                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        )
    }
}
