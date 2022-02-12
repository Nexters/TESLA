package com.ozcoin.cookiepang.ui.onboarding

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentOnBoarding01Binding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoarding01Fragment : BaseFragment<FragmentOnBoarding01Binding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
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
            splashActivityViewModel.finishActivity()
        }
    }

    override fun initObserve() {
        with(onBoarding01FragmentViewModel) {
            lifecycleScope.launch {
                eventFlow.collect { handleEvent(it) }
            }
        }
    }

    override fun init() {
    }
}
