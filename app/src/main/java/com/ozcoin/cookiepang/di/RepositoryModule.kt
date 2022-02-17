package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepository
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepositoryImpl
import com.ozcoin.cookiepang.domain.editcookie.EditCookieRepository
import com.ozcoin.cookiepang.domain.editcookie.EditCookieRepositoryImpl
import com.ozcoin.cookiepang.domain.feed.FeedRepository
import com.ozcoin.cookiepang.domain.feed.FeedRepositoryImpl
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepositoryImpl
import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepository
import com.ozcoin.cookiepang.domain.thememode.ThemeModeRepositoryImpl
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.user.UserRepositoryImpl
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepositoryImpl
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
    abstract fun bindUserReg(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindKlipAuthRepository(impl: KlipAuthRepositoryImpl): KlipAuthRepository

    @Binds
    abstract fun bindUserCategoryRepository(impl: UserCategoryRepositoryImpl): UserCategoryRepository

    @Binds
    abstract fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

    @Binds
    abstract fun bindCookieDetailRepository(impl: CookieDetailRepositoryImpl): CookieDetailRepository

    @Binds
    abstract fun bindEditCookieRepository(impl: EditCookieRepositoryImpl): EditCookieRepository
}
