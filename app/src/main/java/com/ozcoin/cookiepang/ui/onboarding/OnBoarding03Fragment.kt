package com.ozcoin.cookiepang.ui.onboarding

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentOnBoarding03Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding03Fragment : BaseFragment<FragmentOnBoarding03Binding>() {

    private val onBoarding03FragmentViewModel by viewModels<OnBoarding03FragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_on_boarding_03
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.includeTitleLayout.tvSkipBtn.setOnClickListener {
            onBoarding03FragmentViewModel.clickSkip()
        }
    }

    override fun initObserve() {
        observeEvent(onBoarding03FragmentViewModel)
    }

    override fun init() {
    }
}
