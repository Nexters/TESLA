package com.ozcoin.cookiepang

import android.app.Application
import com.ozcoin.cookiepang.domain.thememode.ThemeMode
import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject lateinit var themeModeRepository: ThemeModeRepository

    var onBoardingPageSelectedMyHome: Boolean? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        themeModeRepository.setThemeMode(ThemeMode.LIGHT)
    }
}
