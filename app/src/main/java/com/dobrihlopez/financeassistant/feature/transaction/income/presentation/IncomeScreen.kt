package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.bottomAppBarFabElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreen(component: IncomeComponent) {
    val childStack = component.childStack

    var onActionButtonClick by remember {
        mutableStateOf({})
    }

    var onFabButtonClick by remember {
        mutableStateOf({})
    }

    var topBarTitle by remember {
        mutableStateOf("")
    }

    var actionButtonResId by remember {
        mutableStateOf<Int?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = topBarTitle, style = MaterialTheme.typography.titleLarge)
                    }
                },
                actions = {
                    IconButton(onClick = onActionButtonClick) {
                        val resId = actionButtonResId
                        AnimatedVisibility(
                            visible = resId != null,
                            enter = fadeIn() + expandHorizontally(clip = false, expandFrom = Alignment.Start),
                        ) {
                            Icon(
                                ImageVector.vectorResource(resId!!),
                                contentDescription = null,
                            )
                        }
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                elevation = bottomAppBarFabElevation(),
                shape = CircleShape,
                onClick = onFabButtonClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
    ) { paddingValues ->
        Children(
            stack = childStack,
            animation = stackAnimation(animator = slide(orientation = Orientation.Vertical)),
        ) { child ->
            when (val instance = child.instance) {
                is IncomeComponent.Child.Main -> {
                    topBarTitle = stringResource(R.string.incomes_topbar_title)

                    LaunchedEffect(Unit) {
                        onFabButtonClick = component::onFabClick
                        onActionButtonClick = component::onHistoryClick
                        actionButtonResId = R.drawable.ic_history
                    }

                    IncomeContent(
                        state = component.state.collectAsStateWithLifecycle().value,
                        paddingValues = paddingValues,
                    )
                }

                is IncomeComponent.Child.History -> {
                    topBarTitle = stringResource(R.string.history_topbar_title)

                    LaunchedEffect(Unit) {
                        onFabButtonClick = { }
                        onActionButtonClick = { }
                        actionButtonResId = R.drawable.ic_history_rectangle
                    }

                    HistoryScreen(instance.component, paddingValues)
                }
            }
        }
    }
}
