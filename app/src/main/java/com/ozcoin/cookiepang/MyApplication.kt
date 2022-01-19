package com.ozcoin.cookiepang

import android.app.Application
import com.ozcoin.cookiepang.di.dataSourceModule
import com.ozcoin.cookiepang.di.networkModule
import com.ozcoin.cookiepang.di.repositoryModule
import com.ozcoin.cookiepang.di.viewModelModule
import com.ozcoin.cookiepang.model.ThemeMode
import com.ozcoin.cookiepang.repo.ThemeModeRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    private val themeModeRepository by inject<ThemeModeRepository>()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    repositoryModule,
                    dataSourceModule
                )
            )
        }

        themeModeRepository.setThemeMode(ThemeMode.LIGHT)
    }

}