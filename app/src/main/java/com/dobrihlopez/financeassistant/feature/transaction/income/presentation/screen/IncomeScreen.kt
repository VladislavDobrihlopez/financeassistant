package com.dobrihlopez.financeassistant.feature.transaction.income.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.dobrihlopez.financeassistant.coreui.animation.actionEnterTransition
import com.dobrihlopez.financeassistant.coreui.animation.navActionEnterTransition
import com.dobrihlopez.financeassistant.coreui.animation.navActionExitTransition
import com.dobrihlopez.financeassistant.coreui.composable.Fab
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TopBarDataProvider
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TopBarDataProvider.Default
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TopBarDataProvider.History
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TopBarDataProvider.MainScreen
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TopBarDataProvider.TransactionHandler
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.screen.CreationScreen
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.screen.HistoryScreen
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreen(component: IncomeComponent) {
    val childStack = component.childStack

    var topBarState by remember {
        mutableStateOf<TopBarDataProvider>(Default())
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AnimatedVisibility(topBarState.topBarResId != null, enter = fadeIn()) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(topBarState.topBarResId!!),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = topBarState.actionButtonResId != null && topBarState.onActionButtonClick != null,
                        enter = actionEnterTransition,
                    ) {
                        IconButton(onClick = { topBarState.onActionButtonClick?.invoke() }) {
                            Icon(
                                ImageVector.vectorResource(topBarState.actionButtonResId!!),
                                contentDescription = null,
                            )
                        }
                    }
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visible =
                            topBarState.navigationActionButton != null &&
                                topBarState.onNavigationButtonClick != null,
                        enter = navActionEnterTransition,
                        exit = navActionExitTransition,
                    ) {
                        topBarState.navigationActionButton?.let {
                            IconButton(onClick = { topBarState.onNavigationButtonClick?.invoke() }) {
                                Icon(
                                    imageVector = it,
                                    contentDescription = null,
                                )
                            }
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
            Fab(onClick = topBarState.onFabClick)
        },
    ) { paddingValues ->
        Children(
            stack = childStack,
            animation =
                stackAnimation(selector = { destination ->
                    if (destination.instance is IncomeComponent.Child.TransactionCreator) {
                        slide(orientation = Orientation.Horizontal)
                    } else {
                        slide(orientation = Orientation.Vertical)
                    }
                }),
        ) { child ->
            when (val instance = child.instance) {
                is IncomeComponent.Child.Main -> {
                    topBarState =
                        MainScreen(
                            topBarResId = R.string.incomes_topbar_title,
                            onActionButtonClick = component::onHistoryClick,
                            onFabClick = component::onFabClick,
                        )

                    IncomeContent(
                        state = component.state.collectAsStateWithLifecycle().value,
                        paddingValues = paddingValues,
                        onTransactionClicked = instance.component::onIncomeClick,
                    )
                }

                is IncomeComponent.Child.History -> {
                    topBarState =
                        History(
                            onActionButtonClick = {},
                            onNavigationButtonClick = component::onNavigateBack,
                        )

                    HistoryScreen(instance.component, paddingValues)
                }

                is IncomeComponent.Child.TransactionCreator -> {
                    topBarState =
                        TransactionHandler(
                            onActionButtonClick = instance.component::applyChanges,
                            onNavigationButtonClick = component::onNavigateBack,
                            topBarResId = R.string.operation_tranction_my_incomes,
                        )

                    CreationScreen(instance.component, paddingValues)
                }
            }
        }
    }
}
