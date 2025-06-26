package com.dobrihlopez.financeassistant.feature.settings.presentation.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.item.BasicListItem
import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem

@Composable
fun SettingItem(
    section: AppSettingItem,
    sectionName: String,
    onClick: (AppSettingItem) -> Unit,
) {
    when (section) {
        is AppSettingItem.Theme ->
            BasicListItem(
                modifier = Modifier.height(56.dp),
                content = sectionName,
                trailingContent = {
                    Switch(checked = isSystemInDarkTheme(), onCheckedChange = { onClick(section) })
                },
            )

        else ->
            BasicListItem(
                modifier = Modifier.height(56.dp),
                onClick = { onClick(section) },
                content = sectionName,
                trailingContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.arrow_right), contentDescription = null)
                },
            )
    }
}
