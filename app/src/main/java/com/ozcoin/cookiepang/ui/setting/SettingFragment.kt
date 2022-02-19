package com.ozcoin.cookiepang.ui.setting

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

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
        observeEvent(settingFragmentViewModel)
    }

    override fun init() {
    }
}