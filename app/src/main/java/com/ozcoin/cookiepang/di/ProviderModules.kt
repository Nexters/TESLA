package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.provider.SharedPrefProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providerModule = module {
    single { SharedPrefProvider(androidContext()) }
}