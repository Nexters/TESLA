package com.ozcoin.cookiepang.data

import android.content.Context
import com.ozcoin.cookiepang.common.PREF_STORE_USER
import com.ozcoin.cookiepang.common.PREF_THEME_MODE
import com.ozcoin.cookiepang.model.ThemeMode


class ThemeModeLocalDataSource(
    private val context: Context
) {
    private val userPref = context.getSharedPreferences(PREF_STORE_USER, Context.MODE_PRIVATE)
    private val userPrefEdit = userPref.edit()

    fun getThemeMode(): ThemeMode {
        return userPref.getString(PREF_THEME_MODE, ThemeMode.DEFAULT.name)
            ?.let { ThemeMode.valueOf(it) } ?: ThemeMode.DEFAULT
    }

    fun setThemeMode(themeMode: ThemeMode) {
        userPrefEdit.putString(PREF_THEME_MODE, themeMode.name).commit()
    }

}
