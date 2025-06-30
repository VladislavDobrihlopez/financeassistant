package com.dobrihlopez.financeassistant.feature.settings.domain

import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem

fun interface SettingsProvider {
    fun provide(item: AppSettingItem): String
}
