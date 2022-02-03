package com.ozcoin.cookiepang.data.thememode

import com.ozcoin.cookiepang.domain.thememode.ThemeMode

enum class ThemeModeEntity {
    DEFAULT, LIGHT, DARK
}

fun ThemeModeEntity.toDomain(): ThemeMode {
    return when (this.name) {
        ThemeMode.DEFAULT.name -> ThemeMode.DEFAULT
        ThemeMode.LIGHT.name -> ThemeMode.LIGHT
        ThemeMode.DARK.name -> ThemeMode.DARK
        else -> ThemeMode.DEFAULT
    }
}

fun ThemeMode.toData(): ThemeModeEntity {
    return when (this.name) {
        ThemeModeEntity.DEFAULT.name -> ThemeModeEntity.DEFAULT
        ThemeModeEntity.LIGHT.name -> ThemeModeEntity.LIGHT
        ThemeModeEntity.DARK.name -> ThemeModeEntity.DARK
        else -> ThemeModeEntity.DEFAULT
    }
}
