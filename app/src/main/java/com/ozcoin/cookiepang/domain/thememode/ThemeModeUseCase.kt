package com.ozcoin.cookiepang.domain.thememode

class ThemeModeUseCase(
    private val themeModeRepository: ThemeModeRepository
) {
    fun getThemeMode(): ThemeMode {
        return themeModeRepository.getThemeMode()
    }

    fun setThemeMode(themeMode: ThemeMode) {
        themeModeRepository.setThemeMode(themeMode)
    }

    fun saveUserSetting(themeMode: ThemeMode) {
        themeModeRepository.saveUserSetting(themeMode)
    }
}
