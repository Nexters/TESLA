package com.ozcoin.cookiepang.ui.login

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentLoginBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val loginFragmentViewModel by viewModels<LoginFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        with(binding) {
            viewModel = loginFragmentViewModel
        }
    }

    override fun initObserve() {
        observeEvent(loginFragmentViewModel)
    }

    override fun initListener() {
        lifecycle.addObserver(loginFragmentViewModel)
    }

    override fun init() {
        loginFragmentViewModel.regUserAddress = splashActivityViewModel::setUserAddress
    }
}
