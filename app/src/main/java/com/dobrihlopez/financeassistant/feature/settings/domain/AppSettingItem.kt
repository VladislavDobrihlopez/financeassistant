package com.dobrihlopez.financeassistant.feature.settings.domain

sealed class AppSettingItem(val id: String) {
    data object Theme : AppSettingItem("theme")
    data object PrimaryColor : AppSettingItem("primary_color")
    data object Sound : AppSettingItem("sound")
    data object Haptics : AppSettingItem("haptics")
    data object EnterPassword : AppSettingItem("enter_password")
    data object ServerSync : AppSettingItem("server_sync")
    data object Language : AppSettingItem("language")
    data object About : AppSettingItem("about")

    companion object {
        @JvmField
        val all = listOf(Theme, PrimaryColor, Sound, Haptics, EnterPassword, ServerSync, Language, About)
    }
}
