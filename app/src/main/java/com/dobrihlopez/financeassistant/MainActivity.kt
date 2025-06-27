package com.dobrihlopez.financeassistant

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.defaultComponentContext
import com.dobrihlopez.financeassistant.core_remote.network.ConnectivityObserver
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.RootComponent
import com.dobrihlopez.financeassistant.feature.RootScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var rootComponentFactory: RootComponent.Factory

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    private var isSplashVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        isSplashVisible = savedInstanceState?.getBoolean(EXTRA_SPLASH_VISIBILITY, false) ?: false
        installSplashScreen().setKeepOnScreenCondition {
            isSplashVisible
        }

        val rootComponent =
            rootComponentFactory.create(defaultComponentContext(), onExitApp = {
                finishAffinity()
            })

        setContent {
            FinanceAssistantTheme {
                LaunchedEffect(Unit) {
                    delay(SPLASH_DURATION_IN_MS)
                    isSplashVisible = false
                }

                val hasInternet =
                    connectivityObserver.observe().collectAsStateWithLifecycle(false)

                RootScreen(rootComponent, hasInternet)
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
        const val SPLASH_DURATION_IN_MS = 1000L
    }
}
