package com.dobrihlopez.financeassistant.core_ui.composable

import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.dobrihlopez.financeassistant.R

@Composable
fun ErrorSnackbarHost(
    errorResId: Int?,
    errorMessage: String? = null,
    onRetry: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val context = LocalContext.current
    LaunchedEffect(errorResId, errorMessage) {
        val message = errorResId?.let { context.getString(it) }
            ?: errorMessage
            ?: context.getString(R.string.error_network_try_again)
        val result = snackbarHostState.showSnackbar(
            duration = SnackbarDuration.Short,
            message = message,
//            actionLabel = context.getString(R.string.retry)
        )
        if (result == SnackbarResult.ActionPerformed) {
            onRetry()
            Toast.makeText(context, "To be added", Toast.LENGTH_SHORT).show()
        }
    }
    SnackbarHost(snackbarHostState)
} 