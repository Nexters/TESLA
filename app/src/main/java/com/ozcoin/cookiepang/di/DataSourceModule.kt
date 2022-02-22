package com.ozcoin.cookiepang.di

import android.content.Context
import com.ozcoin.cookiepang.data.ask.AskRemoteDataSource
import com.ozcoin.cookiepang.data.category.CategoryRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailRemoteDataSource
import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource
import com.ozcoin.cookiepang.data.klip.KlipContractTxDataSource
import com.ozcoin.cookiepang.data.provider.AppSettingPrefProvider
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import com.ozcoin.cookiepang.data.provider.UserPrefProvider
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.data.thememode.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.data.timeline.TimeLineRemoteDataSource
import com.ozcoin.cookiepang.data.user.UserLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRemoteDataSource
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
    fun provideUserLocal(userPrefProvider: UserPrefProvider): UserLocalDataSource {
        return UserLocalDataSource(userPrefProvider)
    }

    @Provides
    fun provideUserRemote(apiService: ApiService): UserRemoteDataSource {
        return UserRemoteDataSource(apiService)
    }

    @Provides
    fun provideKlipAuth(
        @ApplicationContext context: Context,
        resourceProvider: ResourceProvider
    ): KlipAuthDataSource {
        return KlipAuthDataSource(context, resourceProvider)
    }

    @Provides
    fun provideKlipContractTx(
        @ApplicationContext context: Context,
        resourceProvider: ResourceProvider
    ): KlipContractTxDataSource {
        return KlipContractTxDataSource(context, resourceProvider)
    }

    @Provides
    fun provideCategoryRemote(apiService: ApiService): CategoryRemoteDataSource {
        return CategoryRemoteDataSource(apiService)
    }

    @Provides
    fun provideTimeLineRemote(apiService: ApiService): TimeLineRemoteDataSource {
        return TimeLineRemoteDataSource(apiService)
    }

    @Provides
    fun provideCookieRemote(apiService: ApiService): CookieRemoteDataSource {
        return CookieRemoteDataSource(apiService)
    }

    @Provides
    fun provideCookieDetailRemote(apiService: ApiService): CookieDetailRemoteDataSource {
        return CookieDetailRemoteDataSource(apiService)
    }

    @Provides
    fun provideAskRemote(apiService: ApiService): AskRemoteDataSource {
        return AskRemoteDataSource(apiService)
    }
}
