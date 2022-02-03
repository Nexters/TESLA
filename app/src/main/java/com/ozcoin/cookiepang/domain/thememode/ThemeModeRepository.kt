package com.ozcoin.cookiepang.domain.thememode

/**
 * 시스템 설정 - 다크 모드
 */
interface ThemeModeRepository {

    fun getThemeMode(): ThemeMode

    fun setThemeMode(themeMode: ThemeMode)

    fun saveUserSetting(themeMode: ThemeMode)
}
