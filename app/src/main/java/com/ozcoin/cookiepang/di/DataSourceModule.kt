package com.ozcoin.cookiepang.di

import android.content.Context
import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource
import com.ozcoin.cookiepang.data.provider.AppSettingPrefProvider
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import com.ozcoin.cookiepang.data.provider.UserPrefProvider
import com.ozcoin.cookiepang.data.thememode.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    fun provideThemeModeLocal(appSettingPrefProvider: AppSettingPrefProvider): ThemeModeLocalDataSource {
        return ThemeModeLocalDataSource(appSettingPrefProvider)
    }

    @Provides
    fun provideUserRegLocal(userPrefProvider: UserPrefProvider): UserRegLocalDataSource {
        return UserRegLocalDataSource(userPrefProvider)
    }

    @Provides
    fun provideKlipAuth(
        @ApplicationContext context: Context,
        resourceProvider: ResourceProvider,
        userPrefProvider: UserPrefProvider
    ): KlipAuthDataSource {
        return KlipAuthDataSource(context, resourceProvider, userPrefProvider)
    }
}
