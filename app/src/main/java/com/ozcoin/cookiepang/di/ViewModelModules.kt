package com.ozcoin.cookiepang.di

import androidx.lifecycle.SavedStateHandle
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.home.HomeFragmentViewModel
import com.ozcoin.cookiepang.ui.login.LoginFragmentViewModel
import com.ozcoin.cookiepang.ui.selectcategory.SelectCategoryFragmentViewModel
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (state: SavedStateHandle) ->
        MainActivityViewModel(state)
    }
    viewModel {
        SplashActivityViewModel(
            userRegRepository = get()
        )
    }
    viewModel {
        LoginFragmentViewModel(
            userRegRepository = get()
        )
    }
    viewModel {
        SelectCategoryFragmentViewModel()
    }
    viewModel {
        HomeFragmentViewModel()
    }
}