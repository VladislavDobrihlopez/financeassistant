package com.dobrihlopez.financeassistant.core_ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.core_ui.ui.theme.spacing

@Composable
fun InternetConnectionStatus(hasInternet: Boolean, modifier: Modifier = Modifier) {
    val error = MaterialTheme.colorScheme.error
    val spacing = MaterialTheme.spacing.medium

    AnimatedVisibility(
        !hasInternet,
        enter = fadeIn(tween()) + expandIn(),
        exit = fadeOut(tween(700)) + shrinkOut(tween(700))
    ) {
        Row(
            modifier = modifier
                .height(52.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .drawBehind {
                    if (!hasInternet) drawRect(error) else drawRect(Color.Green)
                }
                .padding(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "no internet",
                tint = MaterialTheme.colorScheme.onError
            )
            Spacer(modifier = Modifier.width(spacing))
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.error_internet_issues),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = if (!hasInternet) MaterialTheme.colorScheme.onError else Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInternetConnectionStatus_no_internet() {
    FinanceAssistantTheme {
        InternetConnectionStatus(hasInternet = false)
    }
}



