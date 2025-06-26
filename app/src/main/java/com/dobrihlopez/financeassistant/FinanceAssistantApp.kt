package com.dobrihlopez.financeassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinanceAssistantApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        Thread.setDefaultUncaughtExceptionHandler { _, ex ->
//            Log.e("GLOBAL_ERROR", ex.message.toString() + "\n" + ex.cause.toString())
//            Toast.makeText(this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
//        }
    }
}
