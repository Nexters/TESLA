package com.ozcoin.cookiepang.ui.onboarding

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentOnBoarding02Binding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoarding02Fragment : BaseFragment<FragmentOnBoarding02Binding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
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
            splashActivityViewModel.finishActivity()
        }
    }

    override fun initObserve() {
        with(onBoarding02FragmentViewModel) {
            lifecycleScope.launch {
                eventFlow.collect { handleEvent(it) }
            }
        }
    }

    override fun init() {
    }
}
