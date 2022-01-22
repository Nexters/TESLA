package com.ozcoin.cookiepang.di

import androidx.lifecycle.SavedStateHandle
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.home.HomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel()
    }
    viewModel {
        (state: SavedStateHandle) -> HomeFragmentViewModel(state)
    }
}