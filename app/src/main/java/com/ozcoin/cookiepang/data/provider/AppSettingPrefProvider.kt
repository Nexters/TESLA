package com.ozcoin.cookiepang.data.provider

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ozcoin.cookiepang.common.PREF_NAME_APP_SETTING
import com.ozcoin.cookiepang.common.PREF_SHOWED_ON_BOARDING
import com.ozcoin.cookiepang.common.PREF_THEME_MODE
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingPrefProvider @Inject constructor(
    private val context: Context
) {
    companion object {
        private val themeModeKey = stringPreferencesKey(PREF_THEME_MODE)
        private val showedOnBoardingKey = booleanPreferencesKey(PREF_SHOWED_ON_BOARDING)
    }

    private val Context.appSettingPref by preferencesDataStore(PREF_NAME_APP_SETTING)

    suspend fun setThemeMode(themeMode: String) {
        context.appSettingPref.edit {
            it[themeModeKey] = themeMode
        }
    }

    fun getThemeMode() = context.appSettingPref.data.map { pref ->
        pref[themeModeKey]
    }

    suspend fun setShowedOnBoarding(isShowedOnBoarding: Boolean) {
        context.appSettingPref.edit {
            it[showedOnBoardingKey] = isShowedOnBoarding
        }
    }

    fun isShowedOnBoarding() = context.appSettingPref.data.map { pref ->
        pref[showedOnBoardingKey]
    }
}
