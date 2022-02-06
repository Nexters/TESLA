package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.data.provider.SharedPrefProvider
import com.ozcoin.cookiepang.data.thememode.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
