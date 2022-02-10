package com.ozcoin.cookiepang.data.provider

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ozcoin.cookiepang.common.PREF_NAME_USER
import com.ozcoin.cookiepang.common.PREF_USER_KLIP_ADDRESS
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPrefProvider @Inject constructor(
    private val context: Context
) {

    private val Context.userPref by preferencesDataStore(PREF_NAME_USER)
    private val userKlipAddressKey = stringPreferencesKey(PREF_USER_KLIP_ADDRESS)

    suspend fun setUserKlipAddress(userKlipAddress: String) {
        context.userPref.edit {
            it[userKlipAddressKey] = userKlipAddress
        }
    }

    fun getUserKlipAddress() = context.userPref.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[userKlipAddressKey]
        }
}
