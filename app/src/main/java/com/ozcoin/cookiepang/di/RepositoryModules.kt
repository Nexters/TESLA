package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepository
import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepositoryImpl
import com.ozcoin.cookiepang.domain.user.UserRegRepository
import com.ozcoin.cookiepang.domain.user.UserRegRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<ThemeModeRepository> {
        ThemeModeRepositoryImpl(
            themeModeLocalDataSource = get()
        )
    }

    factory<UserRegRepository> {
        UserRegRepositoryImpl(
            userRegLocalDataSource = get()
        )
    }
}
