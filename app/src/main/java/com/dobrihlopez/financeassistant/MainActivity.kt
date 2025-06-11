package com.dobrihlopez.financeassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.RootScreen

class MainActivity : ComponentActivity() {
    private val componentContext: ComponentContext by lazy { defaultComponentContext() }
    private val rootComponent by lazy {
        RootComponent.DefaultRootComponent(
            componentContext,
            LoggingStoreFactory(DefaultStoreFactory())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FinanceAssistantTheme {
                RootScreen(rootComponent)
            }
        }
    }
}
