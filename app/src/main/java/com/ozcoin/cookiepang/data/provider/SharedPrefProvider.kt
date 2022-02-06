package com.ozcoin.cookiepang.data.provider

import android.content.Context
import com.ozcoin.cookiepang.common.PREF_STORE_APP_SETTING
import com.ozcoin.cookiepang.common.PREF_STORE_USER
import com.ozcoin.cookiepang.common.PREF_THEME_MODE
import com.ozcoin.cookiepang.common.PREF_USER_NAME
import com.ozcoin.cookiepang.data.thememode.ThemeModeEntity
import javax.inject.Inject

class SharedPrefProvider @Inject constructor(
    context: Context
) {
    private val appSettingPref = context.getSharedPreferences(PREF_STORE_APP_SETTING, Context.MODE_PRIVATE)
    private val appSettingPrefEdit = appSettingPref.edit()

    private val userPref = context.getSharedPreferences(PREF_STORE_USER, Context.MODE_PRIVATE)
    private val userPrefEdit = userPref.edit()

    fun getThemeMode(): String? = appSettingPref.getString(PREF_THEME_MODE, ThemeModeEntity.DEFAULT.name)

    fun setThemeMode(themeMode: String) {
        appSettingPrefEdit.putString(PREF_THEME_MODE, themeMode).commit()
    }

    fun setUserName(userName: String) {
        userPrefEdit.putString(PREF_USER_NAME, userName).commit()
    }

    fun getUserName(): String? = userPref.getString(PREF_USER_NAME, "")
}
