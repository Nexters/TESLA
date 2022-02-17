package com.ozcoin.cookiepang.ui.onboarding

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentOnBoarding01Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding01Fragment : BaseFragment<FragmentOnBoarding01Binding>() {

    private val onBoarding01FragmentViewModel by viewModels<OnBoarding01FragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_on_boarding_01
    }

    override fun initView() {
        with(binding) {
            showSkipBtn = true
            viewModel = onBoarding01FragmentViewModel
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            onBoarding01FragmentViewModel.clickBack()
        }
        binding.includeTitleLayout.tvSkipBtn.setOnClickListener {
            onBoarding01FragmentViewModel.navigateToMain()
        }
    }

    override fun initObserve() {
        observeEvent(onBoarding01FragmentViewModel)
    }

    override fun init() {
    }
}
