package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.data.ThemeModeLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import com.ozcoin.cookiepang.provider.SharedPrefProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    factory {
        ThemeModeLocalDataSource(
            sharedPrefProvider = get()
        )
    }
    factory {
        UserRegLocalDataSource(
            sharedPrefProvider = get()
        )
    }
}