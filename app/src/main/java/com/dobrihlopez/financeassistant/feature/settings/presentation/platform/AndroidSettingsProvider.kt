package com.dobrihlopez.financeassistant.feature.settings.presentation.platform

import android.content.Context
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.feature.settings.domain.SettingsProvider
import com.dobrihlopez.financeassistant.feature.settings.domain.model.AppSettingItem

class AndroidSettingsProvider(
    private val context: Context
): SettingsProvider {

    override fun provide(item: AppSettingItem): String {
        return context.run {
            when (item) {
                AppSettingItem.About -> getString(R.string.settings_about)
                AppSettingItem.EnterPassword -> getString(R.string.settings_access_password)
                AppSettingItem.Haptics -> getString(R.string.settings_touch_gestures)
                AppSettingItem.Language -> getString(R.string.settings_language)
                AppSettingItem.PrimaryColor -> getString(R.string.settings_primary_color)
                AppSettingItem.ServerSync -> getString(R.string.settings_server_sync)
                AppSettingItem.Sound -> getString(R.string.settings_sound)
                AppSettingItem.Theme -> getString(R.string.settings_theme)
            }
        }
    }
}