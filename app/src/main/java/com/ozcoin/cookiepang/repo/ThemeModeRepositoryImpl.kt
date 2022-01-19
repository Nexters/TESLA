package com.ozcoin.cookiepang.repo

import androidx.appcompat.app.AppCompatDelegate
import com.ozcoin.cookiepang.data.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.model.ThemeMode

class ThemeModeRepositoryImpl(
    private val themeModeLocalDataSource: ThemeModeLocalDataSource
): ThemeModeRepository {

    override fun getThemeMode(): ThemeMode {
        return themeModeLocalDataSource.getThemeMode()
    }

    override fun setThemeMode(themeMode: ThemeMode) {
        val mode = when(themeMode) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun saveUserSetting(themeMode: ThemeMode) {
        themeModeLocalDataSource.setThemeMode(themeMode)
    }

}