package com.ozcoin.cookiepang.domain.thememode

import kotlinx.coroutines.flow.Flow

/**
 * 시스템 설정 - 다크 모드
 */
interface ThemeModeRepository {

    fun getThemeMode(): Flow<ThemeMode>

    fun setThemeMode(themeMode: ThemeMode)

    suspend fun saveUserSetting(themeMode: ThemeMode)
}
