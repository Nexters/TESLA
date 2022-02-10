package com.ozcoin.cookiepang.di

import android.content.Context
import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import com.ozcoin.cookiepang.data.provider.SharedPrefProvider
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
    fun provideThemeModeLocal(sharedPrefProvider: SharedPrefProvider): ThemeModeLocalDataSource {
        return ThemeModeLocalDataSource(sharedPrefProvider)
    }

    @Provides
    fun provideUserRegLocal(sharedPrefProvider: SharedPrefProvider): UserRegLocalDataSource {
        return UserRegLocalDataSource(sharedPrefProvider)
    }

    @Provides
    fun provideKlipAuth(
        @ApplicationContext context: Context,
        resourceProvider: ResourceProvider,
        sharedPrefProvider: SharedPrefProvider
    ): KlipAuthDataSource {
        return KlipAuthDataSource(context, resourceProvider, sharedPrefProvider)
    }
}
