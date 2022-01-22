package com.ozcoin.cookiepang.data

import com.ozcoin.cookiepang.model.ThemeMode
import com.ozcoin.cookiepang.provider.SharedPrefProvider


class ThemeModeLocalDataSource(
    private val sharedPrefProvider: SharedPrefProvider
) {

    fun getThemeMode(): ThemeMode {
        return sharedPrefProvider.getThemeMode()?.let { ThemeMode.valueOf(it) } ?: ThemeMode.DEFAULT
    }

    fun setThemeMode(themeMode: ThemeMode) {
        sharedPrefProvider.setThemeMode(themeMode.name)
    }

}
