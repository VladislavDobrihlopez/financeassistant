package com.dobrihlopez.financeassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.RootScreen

class MainActivity : ComponentActivity() {
    private val rootComponent by lazy {
        RootComponent.DefaultRootComponent(
            defaultComponentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()

        setContent {
            FinanceAssistantTheme {

                RootScreen(rootComponent = rootComponent)
            }
        }
    }
}
