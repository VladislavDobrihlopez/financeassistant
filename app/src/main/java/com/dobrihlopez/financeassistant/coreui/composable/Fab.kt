package com.dobrihlopez.financeassistant.coreui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.bottomAppBarFabElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Fab(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    AnimatedVisibility(
        onClick != null,
        enter = fadeIn() + expandIn(expandFrom = Alignment.BottomCenter, clip = false),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it / 2 })
    ) {
        FloatingActionButton(
            modifier = modifier,
            elevation = bottomAppBarFabElevation(),
            shape = CircleShape,
            onClick = { onClick?.invoke() },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}
