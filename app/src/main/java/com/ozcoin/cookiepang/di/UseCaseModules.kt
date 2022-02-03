package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.domain.thememode.ThemeModeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        ThemeModeUseCase(
            themeModeRepository = get()
        )
    }
}
