package com.ozcoin.cookiepang.base

import android.app.Application
import com.ozcoin.cookiepang.di.networkModule
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                listOf(
                    networkModule
                )
            )
        }
    }

}