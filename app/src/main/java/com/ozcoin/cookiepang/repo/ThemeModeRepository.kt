package com.ozcoin.cookiepang.repo

import com.ozcoin.cookiepang.model.ThemeMode

/**
 * 시스템 설정 - 다크 모드
 */
interface ThemeModeRepository {

    fun getThemeMode() : ThemeMode

    fun setThemeMode(themeMode: ThemeMode)

    fun saveUserSetting(themeMode: ThemeMode)

}