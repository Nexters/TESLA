package com.ozcoin.cookiepang.data.thememode

import com.ozcoin.cookiepang.data.provider.SharedPrefProvider

class ThemeModeLocalDataSource(
    private val sharedPrefProvider: SharedPrefProvider
) {
    fun getThemeMode(): ThemeModeEntity {
        return sharedPrefProvider.getThemeMode()?.let { ThemeModeEntity.valueOf(it) } ?: ThemeModeEntity.DEFAULT
    }

    fun setThemeMode(themeModeEntity: ThemeModeEntity) {
        sharedPrefProvider.setThemeMode(themeModeEntity.name)
    }
}
