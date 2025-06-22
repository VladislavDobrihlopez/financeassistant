package com.dobrihlopez.financeassistant.feature.settings.domain.model

@kotlinx.serialization.Serializable
sealed class AppSettingItem(val id: String) {
    @kotlinx.serialization.Serializable
    data object Theme : AppSettingItem("theme")
    @kotlinx.serialization.Serializable
    data object PrimaryColor : AppSettingItem("primary_color")
    @kotlinx.serialization.Serializable
    data object Sound : AppSettingItem("sound")
    @kotlinx.serialization.Serializable
    data object Haptics : AppSettingItem("haptics")
    @kotlinx.serialization.Serializable
    data object EnterPassword : AppSettingItem("enter_password")
    @kotlinx.serialization.Serializable
    data object ServerSync : AppSettingItem("server_sync")
    @kotlinx.serialization.Serializable
    data object Language : AppSettingItem("language")
    @kotlinx.serialization.Serializable
    data object About : AppSettingItem("about")

    companion object {
        @JvmField
        val all = listOf(Theme, PrimaryColor, Sound, Haptics, EnterPassword, ServerSync, Language, About)
    }
}