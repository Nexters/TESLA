package com.ozcoin.cookiepang.ui.onboarding

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentOnBoarding02Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding02Fragment : BaseFragment<FragmentOnBoarding02Binding>() {

    private val onBoarding02FragmentViewModel by viewModels<OnBoarding02FragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_on_boarding_02
    }

    override fun initView() {
        with(binding) {
            showSkipBtn = true
            viewModel = onBoarding02FragmentViewModel
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            onBoarding02FragmentViewModel.clickBack()
        }
        binding.includeTitleLayout.tvSkipBtn.setOnClickListener {
            onBoarding02FragmentViewModel.navigateToMain()
        }
    }

    override fun initObserve() {
        observeEvent(onBoarding02FragmentViewModel)
    }

    override fun init() {
    }
}
