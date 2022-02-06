package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepository
import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepositoryImpl
import com.ozcoin.cookiepang.domain.user.UserRegRepository
import com.ozcoin.cookiepang.domain.user.UserRegRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindThemeMode(impl: ThemeModeRepositoryImpl): ThemeModeRepository

    @Binds
    abstract fun bindUserReg(impl: UserRegRepositoryImpl): UserRegRepository
}
