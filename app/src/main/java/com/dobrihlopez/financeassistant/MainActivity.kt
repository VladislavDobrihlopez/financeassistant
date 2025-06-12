package com.dobrihlopez.financeassistant

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.RootScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private var isSplashVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        isSplashVisible = savedInstanceState?.getBoolean(EXTRA_SPLASH_VISIBILITY, false) ?: false
        installSplashScreen().setKeepOnScreenCondition {
            isSplashVisible
        }

        val rootComponent = RootComponent.DefaultRootComponent(
            defaultComponentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory()
        )

        setContent {
            FinanceAssistantTheme {
                LaunchedEffect(Unit) {
                    delay(SPLASH_DURATION_IN_MS)
                    isSplashVisible = false
                }

                RootScreen(rootComponent)
            }
        }
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle,
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(EXTRA_SPLASH_VISIBILITY, isSplashVisible)
    }

    private companion object {
        const val EXTRA_SPLASH_VISIBILITY = "extra_splash"
        const val SPLASH_DURATION_IN_MS = 1500L
    }
}
