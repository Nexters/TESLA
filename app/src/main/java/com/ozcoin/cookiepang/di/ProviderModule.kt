package com.ozcoin.cookiepang.di

import android.content.Context
import com.ozcoin.cookiepang.data.provider.AppSettingPrefProvider
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import com.ozcoin.cookiepang.data.provider.UserPrefProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProviderModule {

    @Singleton
    @Provides
    fun provideResource(@ApplicationContext appContext: Context): ResourceProvider {
        return ResourceProvider(appContext)
    }

    @Singleton
    @Provides
    fun provideAppSettingPref(@ApplicationContext appContext: Context): AppSettingPrefProvider {
        return AppSettingPrefProvider(appContext)
    }

    @Singleton
    @Provides
    fun provideUserPref(@ApplicationContext appContext: Context): UserPrefProvider {
        return UserPrefProvider(appContext)
    }
}
