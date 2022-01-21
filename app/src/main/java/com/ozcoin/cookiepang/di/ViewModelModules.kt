package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.ui.MainVm
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainVm()
    }
}