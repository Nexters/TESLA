package com.ozcoin.cookiepang.data.thememode

import com.ozcoin.cookiepang.data.provider.AppSettingPrefProvider
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeModeLocalDataSource @Inject constructor(
    private val appSettingPrefProvider: AppSettingPrefProvider
) {
    fun getThemeMode() = appSettingPrefProvider.getThemeMode().map {
        it?.let { ThemeModeEntity.valueOf(it) } ?: ThemeModeEntity.DEFAULT
    }

    suspend fun setThemeMode(themeModeEntity: ThemeModeEntity) {
        appSettingPrefProvider.setThemeMode(themeModeEntity.name)
    }
}
