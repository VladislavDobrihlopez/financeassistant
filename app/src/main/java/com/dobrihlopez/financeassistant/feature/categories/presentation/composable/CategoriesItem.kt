package com.dobrihlopez.financeassistant.feature.categories.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.coreui.composable.BasicListItem
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

@Composable
fun CategoriesItem(category: Category) {
    BasicListItem(
        modifier = Modifier.height(70.dp),
        content = category.name,
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.emoji,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
    )
}
