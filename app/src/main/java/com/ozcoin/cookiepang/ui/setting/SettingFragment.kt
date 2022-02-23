package com.ozcoin.cookiepang.ui.setting

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSettingBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val settingFragmentViewModel by viewModels<SettingFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_setting
    }

    override fun initView() {
        with(binding) {
            pageName = "설정"
            viewModel = settingFragmentViewModel
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
        lifecycle.addObserver(settingFragmentViewModel)
        observeEvent(settingFragmentViewModel)
        settingFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun init() {
        settingFragmentViewModel.hideBtmMenu = mainActivityViewModel::hideBtmNavView
        settingFragmentViewModel.activityEventObserver = EventObserver(mainActivityViewModel::updateEvent)
    }
}
