package com.ozcoin.cookiepang.data.provider

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ozcoin.cookiepang.common.PREF_NAME_USER
import com.ozcoin.cookiepang.common.PREF_USER_ID
import com.ozcoin.cookiepang.common.PREF_USER_WALLET_ADDRESS
import com.ozcoin.cookiepang.data.user.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPrefProvider @Inject constructor(
    private val context: Context
) {

    private val Context.userPref by preferencesDataStore(PREF_NAME_USER)
    private val userWalletAddressKey = stringPreferencesKey(PREF_USER_WALLET_ADDRESS)
    private val userIdKey = intPreferencesKey(PREF_USER_ID)

    suspend fun setUserEntity(userEntity: UserEntity) {
        context.userPref.edit {
            it[userWalletAddressKey] = userEntity.walletAddress
            it[userIdKey] = userEntity.id
        }
    }

    fun getUserEntity(): Flow<UserEntity?> = context.userPref.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            val walletAddress = it[userWalletAddressKey]
            val userId = it[userIdKey]
            if (userId != null && userId != -1) {
                UserEntity(
                    id = userId,
                    walletAddress = walletAddress ?: "",
                    nickname = "",
                    introduction = ""
                )
            } else {
                null
            }
        }
}
