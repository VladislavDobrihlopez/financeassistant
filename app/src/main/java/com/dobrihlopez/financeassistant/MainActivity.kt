package com.dobrihlopez.financeassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.RootScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = defaultComponentContext()

        setContent {
            FinanceAssistantTheme {
                RootScreen(RootComponent.DefaultRootComponent(rootComponent))
            }
        }
    }
}
