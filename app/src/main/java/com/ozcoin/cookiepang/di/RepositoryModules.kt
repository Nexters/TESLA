package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.repo.ThemeModeRepository
import com.ozcoin.cookiepang.repo.ThemeModeRepositoryImpl
import com.ozcoin.cookiepang.repo.user.UserRegRepository
import com.ozcoin.cookiepang.repo.user.UserRegRepositoryImpl
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