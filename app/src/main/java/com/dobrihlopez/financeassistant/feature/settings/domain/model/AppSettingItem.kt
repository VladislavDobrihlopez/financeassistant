package com.dobrihlopez.financeassistant.feature.settings.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AppSettingItem(val id: String) {
    @Serializable
    data object Theme : AppSettingItem("theme")

    @Serializable
    data object PrimaryColor : AppSettingItem("primary_color")

    @Serializable
    data object Sound : AppSettingItem("sound")

    @Serializable
    data object Haptics : AppSettingItem("haptics")

    @Serializable
    data object EnterPassword : AppSettingItem("enter_password")

    @Serializable
    data object ServerSync : AppSettingItem("server_sync")

    @Serializable
    data object Language : AppSettingItem("language")

    @Serializable
    data object About : AppSettingItem("about")

    companion object {
        @JvmField
        val all = listOf(Theme, PrimaryColor, Sound, Haptics, EnterPassword, ServerSync, Language, About)
    }
}
