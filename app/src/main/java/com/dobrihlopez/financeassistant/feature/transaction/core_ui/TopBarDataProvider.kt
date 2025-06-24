package com.dobrihlopez.financeassistant.feature.transaction.core_ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import com.dobrihlopez.financeassistant.R

interface TopBarDataProvider {
    val actionButtonResId: Int?
    val topBarResId: Int?
    val navigationActionButton: ImageVector?
    val onNavigationButtonClick: (() -> Unit)?
    val onActionButtonClick: (() -> Unit)?
    val onFabClick: (() -> Unit)?

    class Default(
        override val actionButtonResId: Int? = null,
        override val topBarResId: Int? = null,
        override val navigationActionButton: ImageVector? = null,
        override val onNavigationButtonClick: (() -> Unit)? = null,
        override val onActionButtonClick: (() -> Unit)? = null,
        override val onFabClick: (() -> Unit)? = null,
    ): TopBarDataProvider

    class MainScreen(
        override val topBarResId: Int,
        override val onActionButtonClick: () -> Unit,
        override val onFabClick: () -> Unit,
        override val onNavigationButtonClick: (() -> Unit)? = null,
    ) : TopBarDataProvider {
        override val actionButtonResId: Int = R.drawable.ic_history
        override val navigationActionButton: ImageVector? = null
    }

    class History(
        override val onActionButtonClick: () -> Unit,
        override val onNavigationButtonClick: () -> Unit,
    ) : TopBarDataProvider {
        override val actionButtonResId: Int = R.drawable.ic_history_rectangle
        override val topBarResId: Int = R.string.history_topbar_title
        override val navigationActionButton: ImageVector? =
            Icons.AutoMirrored.Default.ArrowBack
        override val onFabClick: (() -> Unit)? = null
    }
}
