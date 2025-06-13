package com.dobrihlopez.financeassistant.feature.settings.domain

fun interface SettingsProvider {
    fun provide(item: AppSettingItem): String
}
