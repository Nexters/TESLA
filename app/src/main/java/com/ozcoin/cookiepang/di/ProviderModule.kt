package com.ozcoin.cookiepang.di

import android.content.Context
import com.ozcoin.cookiepang.data.provider.SharedPrefProvider
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
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPrefProvider {
        return SharedPrefProvider(appContext)
    }
}
