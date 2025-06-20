package com.dobrihlopez.financeassistant

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinanceAssistantApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, _ ->
            Toast.makeText(this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
        }
    }
}
