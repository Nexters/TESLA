package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.data.ThemeModeLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single { ThemeModeLocalDataSource(androidContext()) }
}