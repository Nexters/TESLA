package com.ozcoin.cookiepang.domain.thememode

import androidx.appcompat.app.AppCompatDelegate
import com.ozcoin.cookiepang.data.thememode.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.data.thememode.toData
import com.ozcoin.cookiepang.data.thememode.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeModeRepositoryImpl @Inject constructor(
    private val themeModeLocalDataSource: ThemeModeLocalDataSource
) : ThemeModeRepository {

    override fun getThemeMode(): Flow<ThemeMode> {
        return themeModeLocalDataSource.getThemeMode().map { it.toDomain() }
    }

    override fun setThemeMode(themeMode: ThemeMode) {
        val mode = when (themeMode) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override suspend fun saveUserSetting(themeMode: ThemeMode) {
        themeModeLocalDataSource.setThemeMode(themeMode.toData())
    }
}
